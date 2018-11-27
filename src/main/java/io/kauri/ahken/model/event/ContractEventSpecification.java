package io.kauri.ahken.model.event;

import java.util.List;
import lombok.Data;

@Data
public class ContractEventSpecification {

    private String eventName;

    private List<ParameterDefinition> indexedParameterDefinitions;

    private List<ParameterDefinition> nonIndexedParameterDefinitions;
}
