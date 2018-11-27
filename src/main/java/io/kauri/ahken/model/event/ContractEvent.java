package io.kauri.ahken.model.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.kauri.ahken.model.event.parameter.EventParameter;
import java.math.BigInteger;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractEvent {

    private String name;

    private String filterId;

    private List<EventParameter> indexedParameters;

    private List<EventParameter> nonIndexedParameters;

    private String transactionHash;

    private BigInteger logIndex;

    private BigInteger blockNumber;

    private String blockHash;

    private String address;
}
