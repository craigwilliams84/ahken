package io.kauri.ahken.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kauri.ahken.AhkenException;
import io.kauri.ahken.model.event.ContractEvent;
import io.kauri.ahken.service.EventProcessorService;
import java.io.IOException;
import lombok.AllArgsConstructor;
import net.consensys.eventeum.dto.block.BlockDetails;
import net.consensys.eventeum.dto.event.ContractEventDetails;
import net.consensys.eventeum.integration.broadcast.blockchain.ListenerInvokingBlockchainEventBroadcaster.OnBlockchainEventListener;
import org.springframework.stereotype.Component;

/**
 * OnBlockchainEventLiostener that converts to a native ContractEvent object
 * and delegates to the EventProcessor service.
 *
 * @author Craig Williams
 */
@Component
@AllArgsConstructor
public class AhkenBlockchainEventListener implements OnBlockchainEventListener {

    private EventProcessorService processorService;

    private ObjectMapper objectMapper;

    @Override
    public void onNewBlock(BlockDetails blockDetails) {
        //DO NOTHING
    }

    @Override
    public void onContractEvent(ContractEventDetails contractEventDetails) {
        processorService.processEvent(detailsToContractEvent(contractEventDetails));
    }

    //TODO Improve conversion
    private ContractEvent detailsToContractEvent(ContractEventDetails contractEventDetails) {
        try {
            final String json = objectMapper.writeValueAsString(contractEventDetails);

            return objectMapper.readValue(json, io.kauri.ahken.model.event.ContractEvent.class);
        } catch (IOException e) {
            throw new AhkenException("Unable to convert", e);
        }
    }
}
