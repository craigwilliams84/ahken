package io.kauri.ahken.service;

import io.kauri.ahken.model.event.ContractEventSpecification;
import lombok.AllArgsConstructor;
import net.consensys.eventeum.dto.event.filter.ContractEventFilter;
import net.consensys.eventeum.service.SubscriptionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * An EventSubscriptionService that subscribes to events via Eventeum.
 *
 * @author Craig Williams
 */
@Service
@AllArgsConstructor
public class EventeumEventSubscriptionService implements EventSubscriptionService {

    private SubscriptionService subscriptionService;

    private ModelMapper modelMapper;

    @Override
    public void subscribeToEvent(String address, ContractEventSpecification spec) {
        subscriptionService.registerContractEventFilter(buildContractEventFilter(address, spec));
    }

    private ContractEventFilter buildContractEventFilter(String address, ContractEventSpecification spec) {
        final ContractEventFilter filter = new ContractEventFilter();
        filter.setContractAddress(address);
        filter.setId(spec.getEventName() + "-" + address);
        filter.setEventSpecification(modelMapper.map(spec, net.consensys.eventeum.dto.event.filter.ContractEventSpecification.class));

        return filter;
    }
}
