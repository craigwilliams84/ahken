package io.kauri.ahken.configuration;

import io.kauri.ahken.eventeum.NitrateEventStore;
import io.kauri.ahken.listener.AhkenBlockchainEventListener;
import io.kauri.ahken.eventeum.repository.DoNothingEventFilterRepository;
import net.consensys.eventeum.factory.ContractEventFilterRepositoryFactory;
import net.consensys.eventeum.factory.EventStoreFactory;
import net.consensys.eventeum.integration.broadcast.blockchain.BlockchainEventBroadcaster;
import net.consensys.eventeum.integration.broadcast.blockchain.ListenerInvokingBlockchainEventBroadcaster;
import org.dizitart.no2.Nitrite;
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

    @Bean
    public EventStoreFactory eventStoreFactory(Nitrite nitrite) {
        return () -> new NitrateEventStore(nitrite);
    }
}
