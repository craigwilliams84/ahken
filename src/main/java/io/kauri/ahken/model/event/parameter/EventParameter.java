package io.kauri.ahken.model.event.parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes( {
        @JsonSubTypes.Type(value = StringParameter.class, name = "address"),
        @JsonSubTypes.Type(value = StringParameter.class, name = "bytes32"),
        @JsonSubTypes.Type(value = StringParameter.class, name = "bytes32Hex"),
        @JsonSubTypes.Type(value = StringParameter.class, name = "string"),
        @JsonSubTypes.Type(value = NumberParameter.class, name = "uint8"),
        @JsonSubTypes.Type(value = NumberParameter.class, name = "uint256")
})
public interface EventParameter<T> {
    String getType();

    T getValue();

    Integer getPosition();

    @JsonIgnore
    String getValueString();
}
