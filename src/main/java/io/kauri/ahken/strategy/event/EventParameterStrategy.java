package io.kauri.ahken.strategy.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.kauri.ahken.model.event.ContractEvent;
import io.kauri.ahken.model.event.ContractEventSpecification;
import io.kauri.ahken.model.event.ParameterDefinition;
import io.kauri.ahken.model.event.parameter.AbstractEventParameter;
import io.kauri.ahken.model.event.parameter.EventParameter;
import io.kauri.ahken.model.ipfs.IPFSHash;
import io.kauri.ahken.strategy.IPFSStrategyExecutionException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * An EventBasedStrategy that reads IPFS hashes from parameters within an emitting
 * smart contract event.
 *
 * The hashPositions field defines the zero indexed list of event parameters that contain
 * IPFS hashes that should be pinned.
 *
 * The idPosition field indicates the parameter position that should match the users
 * personal identifier (usually public address).
 *
 * @author Craig Williams
 */
@Data
@NoArgsConstructor
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventParameterStrategy extends EventBasedStrategy {

    Integer idPosition;
    List<Integer> hashPositions;
    String id;

    @JsonIgnore
    ContractEventSpecification eventSpec;

    @Override
    public List<IPFSHash> getIPFSHashes(ContractEvent contractEvent) {
        populateParameterPositions(contractEvent);

        final Optional<EventParameter> potentialIdParameter = getParameterAtPosition(contractEvent, idPosition);

        final EventParameter idParameter = potentialIdParameter
                .orElseThrow(() -> new IPFSStrategyExecutionException("Event does not have parameter at id position"));

        if (idParameter.getValueString().equalsIgnoreCase(id)) {
            return getIPFSHashesFromEvent(contractEvent);
        } else {
            log.debug("Received event that did not match ");
        }

        return Collections.emptyList();
    }

    private List<EventParameter> getAllEventParameters(ContractEvent contractEvent) {
        final List<EventParameter> allParameters = new ArrayList(contractEvent.getIndexedParameters());
        allParameters.addAll(contractEvent.getNonIndexedParameters());

        return allParameters;
    }

    private Optional<EventParameter> getParameterAtPosition(ContractEvent contractEvent, int position) {
        return getAllEventParameters(contractEvent)
                .stream()
                .filter(param -> param.getPosition().equals(position))
                .findFirst();
    }

    private List<IPFSHash> getIPFSHashesFromEvent(ContractEvent contractEvent) {
        return getAllEventParameters(contractEvent)
                .stream()
                .filter(parameter -> hashPositions.contains(parameter.getPosition()))
                .map(parameter -> IPFSHash.of(parameter.getValueString()))
                .collect(Collectors.toList());
    }

    private void populateParameterPositions(ContractEvent contractEvent) {
        populateParameterPositions(
                contractEvent.getNonIndexedParameters(), eventSpec.getNonIndexedParameterDefinitions());

        populateParameterPositions(
                contractEvent.getIndexedParameters(), eventSpec.getIndexedParameterDefinitions());
    }

    private void populateParameterPositions(List<EventParameter> eventParameters,
                                            List<ParameterDefinition> specParameters) {
        for (int i = 0; i < eventParameters.size(); i++) {
            final AbstractEventParameter eventParameter =
                    (AbstractEventParameter) eventParameters.get(i);

            eventParameter.setPosition(specParameters.get(i).getPosition());
        }
    }
}
