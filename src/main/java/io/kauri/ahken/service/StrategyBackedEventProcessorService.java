package io.kauri.ahken.service;

import io.kauri.ahken.model.event.ContractEvent;
import io.kauri.ahken.model.ipfs.IPFSHash;
import io.kauri.ahken.strategy.IPFSHashStrategy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.stereotype.Service;

/**
 * An EventProcessorService that pins IPFS hashes based on the returned list of a
 * Strategy object.
 *
 * @author Craig Williams
 */
@Service
@AllArgsConstructor
public class StrategyBackedEventProcessorService implements EventProcessorService {

    private IPFSService ipfsService;

    private Map<ContractAddressAndEventName, IPFSHashStrategy<ContractEvent>> strategies = new ConcurrentHashMap<>();

    @Override
    public void processEvent(ContractEvent event) {
        final IPFSHashStrategy<ContractEvent> strategy = strategies.get(
                new ContractAddressAndEventName(event.getAddress(), event.getName()));

        if (strategy != null) {
            final List<IPFSHash> ipfsHashes = strategy.getIPFSHashes(event);

            ipfsHashes.forEach(ipfsHash -> {
                final byte[] content = ipfsService.getContent(ipfsHash.getHash());

                ipfsService.saveContent(content);
            });

        }
    }

    public synchronized void addStrategy(String contractAddress,
                                         String eventName,
                                         IPFSHashStrategy<ContractEvent> strategy) {
        strategies.put(new ContractAddressAndEventName(contractAddress, eventName), strategy);
    }

    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode
    private class ContractAddressAndEventName {
        private String contractAddress;

        private String eventName;
    }
}
