package io.kauri.ahken.strategy;

/**
 * An exception thrown if there was an error when executing a strategy.
 *
 * @author Craig Williams
 */
public class IPFSStrategyExecutionException extends RuntimeException {

    public IPFSStrategyExecutionException(String msg) {
        super(msg);
    }
}
