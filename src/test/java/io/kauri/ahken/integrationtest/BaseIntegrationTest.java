package io.kauri.ahken.integrationtest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import io.ipfs.api.IPFS;
import io.kauri.ahken.service.IPFSService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import junit.framework.TestCase;
import net.consensys.eventeum.utils.JSON;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;

public class BaseIntegrationTest {

    private static FixedHostPortGenericContainer parityContainer;


    protected static final BigInteger GAS_PRICE = BigInteger.valueOf(22_000_000_000L);
    protected static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);

    protected static final String EVENT_NAME = "TestEvent";

    protected static final Credentials CREDS = Credentials.create("0x4d5db4107d237df6a3d58ee5f70ae63d73d7658d4026f2eefd2f204c81682cb7");

    protected static final byte[] CONTENT = "SomeContent".getBytes();

    protected static final String IPFS_HASH = "QmcxXuwa5jPdsQDZroZ2t3hvBE6cRXFPKMSFwbKbY5nZ1S";

    protected static EventEmitter eventEmitter;

    private Web3j web3j;

    private Admin admin;

    protected List<String> ipfsHashesRetrieved;

    protected int pinContentCount;

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @MockBean
    private IPFSService mockIPFSService;

    @MockBean
    private IPFS mockIPFS;

    @BeforeClass
    public static void setupEnvironment() throws Exception {

        parityContainer = new FixedHostPortGenericContainer("kauriorg/parity-docker:latest");
        parityContainer.waitingFor(Wait.forListeningPort());
        parityContainer.withFixedExposedPort(8545, 8545);
        parityContainer.withFixedExposedPort(8546, 8546);
        parityContainer.start();

        waitForParityToStart(10000, Web3j.build(new HttpService("http://localhost:8545")));

        eventEmitter = deployEventEmitterContract();

        setupScript(eventEmitter.getContractAddress());
    }

    @Before
    public void setUp() throws Exception {

        ipfsHashesRetrieved = new ArrayList<>();
        pinContentCount = 0;

        web3j = Web3j.build(new HttpService("http://localhost:8545"));
        admin = Admin.build(new HttpService("http://localhost:8545"));

        when(mockIPFSService.getContent(any(String.class))).then((invocation) -> {
            ipfsHashesRetrieved.add(invocation.getArgument(0));
            return CONTENT;
        });

        when(mockIPFSService.saveContent(CONTENT)).then((invocation) -> {
            pinContentCount++;
            return IPFS_HASH;
        });
    }

    @AfterClass
    public static void teardownEnvironment() {

        parityContainer.stop();
    }

    protected void waitForIPFSPin() {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static EventEmitter deployEventEmitterContract() throws Exception {
        final Web3j web3j = Web3j.build(new HttpService("http://localhost:8545"));
        return EventEmitter.deploy(web3j, CREDS, GAS_PRICE, GAS_LIMIT).send();
    }

    private static void waitForParityToStart(long timeToWait, Web3j web3j) {
        final long startTime = System.currentTimeMillis();

        while (true) {
            if (System.currentTimeMillis() > startTime + timeToWait) {
                throw new IllegalStateException("Parity failed to start...");
            }

            try {
                web3j.web3ClientVersion().send();
                break;
            } catch (Throwable t) {
                //If an error occurs, the node is not yet up
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();;
            }
        }
    }

    private static void setupScript(String contractAddress) throws IOException {
        final String template = getTemplateScriptFromFile();

        final String scriptString = String.format(template, contractAddress);

        Files.write(Paths.get("src/test/resources/jsonScript.json"), scriptString.getBytes());
    }

    private static String getTemplateScriptFromFile() throws FileNotFoundException {
        ClassLoader classLoader = BaseIntegrationTest.class.getClassLoader();
        File file = new File(classLoader.getResource("templateJsonScript.json").getFile());
        return new Scanner(file).useDelimiter("\\Z").next();
    }
}
