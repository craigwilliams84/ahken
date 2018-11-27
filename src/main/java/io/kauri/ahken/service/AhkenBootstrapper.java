package io.kauri.ahken.service;

import io.kauri.ahken.model.RawScript;
import io.kauri.ahken.model.Script;
import io.kauri.ahken.model.event.ContractEvent;
import io.kauri.ahken.script.processor.ScriptParser;
import io.kauri.ahken.script.reader.ScriptReader;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Loads the configured Ahken scripts on startup, and registers them.
 *
 * @author Craig Williams
 */
@Component
@AllArgsConstructor
public class AhkenBootstrapper implements InitializingBean {

    private EventSubscriptionService eventSubscriptionService;

    private StrategyBackedEventProcessorService eventProcessorService;

    private ScriptReader scriptReader;

    private List<ScriptParser> parsers;

    @Override
    public void afterPropertiesSet() throws Exception {
        final List<RawScript> rawScripts = scriptReader.readScriptStreams();

        rawScripts.forEach(rawScript -> {
            final Optional<ScriptParser> potentialParser = parsers.stream()
                    .filter(parser -> parser.getSupportedExtensions().contains(getScriptExtension(rawScript)))
                    .findFirst();

            if (potentialParser.isPresent()) {
                final List<Script<ContractEvent>> scripts = potentialParser.get().parse(rawScript.getStream());

                scripts.forEach(script -> {
                    //Register strategy
                    eventProcessorService.addStrategy(script.getContractAddress(),
                            script.getEventSpecification().getEventName(), script.getIpfsHashStrategy());

                    //Subscribe to event
                    eventSubscriptionService.subscribeToEvent(
                            script.getContractAddress(), script.getEventSpecification());
                });
            }
        });
    }

    private String getScriptExtension(RawScript rawScript) {
        final String identifier = rawScript.getIdentifier();
        return identifier.substring(identifier.indexOf(".") + 1);
    }
}
