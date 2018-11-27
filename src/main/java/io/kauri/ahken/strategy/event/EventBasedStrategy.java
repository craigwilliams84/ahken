package io.kauri.ahken.strategy.event;

import io.kauri.ahken.model.event.ContractEvent;
import io.kauri.ahken.strategy.IPFSHashStrategy;

/**
 * An IPFSHashStrategy that takes a ContractEvent as the input.
 *
 * @author Craig Williams
 */
public abstract class EventBasedStrategy implements IPFSHashStrategy<ContractEvent> {
}
