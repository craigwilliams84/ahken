package io.kauri.ahken.configuration;

import io.kauri.ahken.listener.AhkenBlockchainEventListener;
import io.kauri.ahken.repository.eventeum.DoNothingEventFilterRepository;
import net.consensys.eventeum.factory.ContractEventFilterRepositoryFactory;
import net.consensys.eventeum.integration.broadcast.blockchain.BlockchainEventBroadcaster;
import net.consensys.eventeum.integration.broadcast.blockchain.ListenerInvokingBlockchainEventBroadcaster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures application context for Eventeum specific beans.
 *
 * @author Craig Williams
 */
@Configuration
public class EventeumConfiguration {

    @Bean
    public BlockchainEventBroadcaster listenerBroadcaster(AhkenBlockchainEventListener listener) {
        return new ListenerInvokingBlockchainEventBroadcaster(listener);
    }

    @Bean
    public ContractEventFilterRepositoryFactory eventFilterRepoFactory() {
        return () -> new DoNothingEventFilterRepository();
    }
}
