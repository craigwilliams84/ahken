package io.kauri.ahken.integrationtest;

import static org.junit.Assert.assertEquals;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application-test.properties")
public class AhkenIT extends BaseIntegrationTest {

    @Test
    public void testSinglePin() throws Exception {
        TransactionReceipt receipt = eventEmitter.emitEvent(IPFS_HASH).send();

        waitForIPFSPin();

        assertEquals(1, ipfsHashesRetrieved.size());
        assertEquals(IPFS_HASH, ipfsHashesRetrieved.get(0));

        assertEquals(1, pinContentCount);
    }
}
