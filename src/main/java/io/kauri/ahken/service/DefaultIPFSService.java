package io.kauri.ahken.service;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import io.kauri.ahken.AhkenException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Adapted from net.consensys.tools.ipfs.ipfsstore.dao.storage.IPFSStorageDao.
 *
 * https://github.com/ConsenSys/IPFS-Store
 */
@Service
@Slf4j
public class DefaultIPFSService implements IPFSService {

    private static final int DEFAULT_THREAD_POOL = 10;
    private static final int DEFAULT_TIMEOUT = 120000;

    private final IPFS ipfs;
    private final ExecutorService pool;
    private final Integer timeout;

    @Autowired
    public DefaultIPFSService(IPFS ipfs) {
        this.ipfs = ipfs;

        this.pool = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL);

        this.timeout = DEFAULT_TIMEOUT;
    }

    @Override
    public String saveContent(byte[] content) {

        log.debug("****** STACK: ");
        new Throwable("").printStackTrace();

        log.debug("Store file in IPFS ...");

        try {
            NamedStreamable.ByteArrayWrapper requestFile = new NamedStreamable.ByteArrayWrapper(
                    content);
            MerkleNode response = this.ipfs.add(requestFile).get(0);

            String hash = response.hash.toString();

            log.debug("File created in IPFS: hash={} ", hash);

            return hash;

        } catch (IOException ex) {
            log.error("Exception while storing file in IPFS", ex);
            throw new AhkenException(
                    "Exception while storing file in IPFS", ex);
        }
    }

    @Override
    public byte[] getContent(String hash) {

        log.debug("Get file in IPFS [hash: {}] ", hash);

        try {
            Multihash filePointer = Multihash.fromBase58(hash);

            Future<byte[]> ipfsFetcherResult = pool.submit(() -> ipfs.cat(filePointer));

            byte[] content = ipfsFetcherResult.get(timeout, TimeUnit.MILLISECONDS);

            log.debug("Get file in IPFS [hash: {}]", hash);

            return content;

        } catch (java.util.concurrent.TimeoutException ex) {
            log.error("Timeout Exception while getting file in IPFS [hash: {}, timeout: {} ms]", hash, timeout);
            throw new AhkenException(
                    "Timeout Exception while getting file in IPFS [hash: " + hash + "]", ex);

        } catch (InterruptedException ex) {
            log.error("Interrupted Exception while getting file in IPFS [hash: {}]", hash);
            throw new AhkenException(
                    "Interrupted Exception while getting file in IPFS [hash: " + hash + "]", ex);

        } catch (ExecutionException ex) {
            log.error("Execution Exception while getting file in IPFS [hash: {}]", hash, ex);
            throw new AhkenException(
                    "Execution Exception while getting file in IPFS [hash: " + hash + "]", ex);
        }
    }
}
