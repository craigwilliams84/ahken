package io.kauri.ahken;

/**
 * Generic Ahken exception.
 *
 * @author Craig Williams
 */
public class AhkenException extends RuntimeException {

    public AhkenException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
