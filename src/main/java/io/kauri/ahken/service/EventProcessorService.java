package io.kauri.ahken.service;

import io.kauri.ahken.model.event.ContractEvent;

/**
 * Processes an ContractEvent that has been emitted by a Smart Contract.
 *
 * @author Craig Williams
 */
public interface EventProcessorService {

    void processEvent(ContractEvent event);
}
