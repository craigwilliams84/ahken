package io.kauri.ahken.script.processor;

import io.kauri.ahken.model.Script;
import io.kauri.ahken.model.event.ContractEvent;
import java.io.InputStream;
import java.util.List;

/**
 * Parses a Script from an InputStream.
 *
 * @author Craig Williams
 */
public interface ScriptParser<T> {

    /**
     * @return a list of file extensions that are supported by this parser.
     */
    List<String> getSupportedExtensions();

    /**
     * Consumed a stream InputStream and parses into a List of Script objects.
     *
     * @param scriptStream The script input stream
     * @return Parsed scripts.
     */
    List<Script<T>> parse(InputStream scriptStream);
}
