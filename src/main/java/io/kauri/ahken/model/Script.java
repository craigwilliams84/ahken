package io.kauri.ahken.model;

import io.kauri.ahken.model.event.ContractEventSpecification;
import io.kauri.ahken.strategy.IPFSHashStrategy;

public interface Script<T> {

    String getContractAddress();

    ContractEventSpecification getEventSpecification();

    IPFSHashStrategy<T> getIpfsHashStrategy();
}
