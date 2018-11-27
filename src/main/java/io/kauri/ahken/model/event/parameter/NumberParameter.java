package io.kauri.ahken.model.event.parameter;

import java.math.BigInteger;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NumberParameter extends AbstractEventParameter<BigInteger> {

    public NumberParameter(String type, BigInteger value, Integer position) {
        super(type, value, position);
    }

    @Override
    public String getValueString() {
        return getValue().toString();
    }
}
