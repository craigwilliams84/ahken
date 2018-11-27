package io.kauri.ahken.model.event.parameter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StringParameter extends AbstractEventParameter<String> {

    public StringParameter(String type, String value, Integer position) {
        super(type, value, position);
    }

    @Override
    public String getValueString() {
        return getValue();
    }
}
