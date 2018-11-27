package io.kauri.ahken.script.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kauri.ahken.model.Script;
import io.kauri.ahken.model.event.ContractEvent;
import io.kauri.ahken.model.json.AhkenJSONScript;
import io.kauri.ahken.script.exception.ScriptException;
import io.kauri.ahken.strategy.event.EventParameterStrategy;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Parses the input stream of a json script.
 *
 * @author Craig Williams
 */
@Component
@AllArgsConstructor
public class JSONScriptParser implements ScriptParser<ContractEvent> {

    private ObjectMapper objectMapper;

    @Override
    public List<String> getSupportedExtensions() {
        return Collections.singletonList("json");
    }

    @Override
    public List<Script<ContractEvent>> parse(InputStream scriptStream) {
        try {
            final Script<ContractEvent> script = objectMapper.readValue(scriptStream, AhkenJSONScript.class);

            //Hack!! TODO Remove
            ((EventParameterStrategy) script.getIpfsHashStrategy()).setEventSpec(script.getEventSpecification());

            return Collections.singletonList(script);
        } catch (IOException e) {
            throw new ScriptException("Unable to parse json script", e);
        }
    }
}
