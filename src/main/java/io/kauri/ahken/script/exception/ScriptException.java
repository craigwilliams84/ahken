package io.kauri.ahken.script.exception;

/**
 * An exception thrown on error during script processing.
 *
 * @author Craig Williams
 */
public class ScriptException extends RuntimeException {

    public ScriptException(Throwable cause) {
        super(cause);
    }

    public ScriptException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
