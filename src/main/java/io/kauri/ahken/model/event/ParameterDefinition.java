package io.kauri.ahken.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParameterDefinition implements Comparable<net.consensys.eventeum.dto.event.filter.ParameterDefinition> {

    private Integer position;

    private ParameterType type;

    @Override
    public int compareTo(net.consensys.eventeum.dto.event.filter.ParameterDefinition o) {
        return this.position.compareTo(o.getPosition());
    }
}
