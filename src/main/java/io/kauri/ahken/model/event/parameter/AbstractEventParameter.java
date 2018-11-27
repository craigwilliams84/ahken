package io.kauri.ahken.model.event.parameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractEventParameter<T> implements EventParameter<T> {
    private String type;

    private T value;

    private Integer position;
}
