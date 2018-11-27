package io.kauri.ahken.strategy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.kauri.ahken.model.ipfs.IPFSHash;
import io.kauri.ahken.strategy.event.EventParameterStrategy;
import java.util.List;

/**
 * Calculates IPFS hashes to pin, based on an input value of type T.
 *
 * @param <T> The input type
 *
 * @author Craig Williams
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes( {
        @JsonSubTypes.Type(value = EventParameterStrategy.class, name = "eventParameterHash"),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface IPFSHashStrategy<T> {

    /**
     *
     * @param input
     * @return a list of IPFS hashes that should be pinned by Ahken.
     */
    List<IPFSHash> getIPFSHashes(T input);
}
