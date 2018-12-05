package io.kauri.ahken.eventeum;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

import java.util.Collections;
import lombok.AllArgsConstructor;
import net.consensys.eventeum.dto.event.ContractEventDetails;
import net.consensys.eventeum.integration.eventstore.SaveableEventStore;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@AllArgsConstructor
public class NitrateEventStore implements SaveableEventStore {

    private Nitrite nitrite;

    @Override
    public Page<ContractEventDetails> getContractEventsForSignature(String eventSignature, PageRequest pagination) {

        //Eventeum only actually cares about the latest event
        //Should really change the Eventeum eventstore interface
        final ContractEventDetails signatureEvent = getRepository()
                .find(eq("eventSpecificationSignature", eventSignature))
                .firstOrDefault();

        return new PageImpl<>(Collections.singletonList(signatureEvent));
    }

    @Override
    public boolean isPagingZeroIndexed() {
        return true;
    }

    @Override
    public void save(ContractEventDetails contractEventDetails) {
        getRepository().insert(contractEventDetails);
    }

    private ObjectRepository<ContractEventDetails> getRepository() {
        return nitrite.getRepository(ContractEventDetails.class);
    }
}
