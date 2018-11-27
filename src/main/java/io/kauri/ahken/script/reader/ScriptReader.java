package io.kauri.ahken.script.reader;

import io.kauri.ahken.model.RawScript;
import java.util.List;

/**
 * Reads scripts from the system into RawScript objects
 *
 * @author Craig Williams
 */
public interface ScriptReader {

    List<RawScript> readScriptStreams();
}
