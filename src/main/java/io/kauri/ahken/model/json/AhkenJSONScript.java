package io.kauri.ahken.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.kauri.ahken.model.Script;
import io.kauri.ahken.model.event.ContractEvent;
import io.kauri.ahken.model.event.ContractEventSpecification;
import io.kauri.ahken.strategy.IPFSHashStrategy;
import lombok.Data;

@Data
public class AhkenJSONScript implements Script<ContractEvent> {

    @JsonProperty("contractAddress")
    private String contractAddress;

    @JsonProperty("eventSpecification")
    private ContractEventSpecification eventSpecification;

    @JsonProperty("ipfsHashStrategy")
    private IPFSHashStrategy<ContractEvent> ipfsHashStrategy;
}
