package io.kauri.ahken.service;

import io.kauri.ahken.model.event.ContractEventSpecification;
import org.springframework.stereotype.Component;

/**
 * A service used to subscribe to a smart contract event.
 *
 * @author Craig Williams
 */
public interface EventSubscriptionService {

    /**
     * Subscribe to an Ethereum Smart Contract event.
     *
     * @param address The smart contract address
     * @param spec The event specification to subscribe to
     */
    void subscribeToEvent(String address, ContractEventSpecification spec);
}
