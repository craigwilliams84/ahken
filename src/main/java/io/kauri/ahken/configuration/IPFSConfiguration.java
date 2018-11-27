package io.kauri.ahken.configuration;

import io.ipfs.api.IPFS;
import io.kauri.ahken.AhkenSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures application context for IPFS specific beans.
 *
 * @author Craig Williams
 */
@Configuration
public class IPFSConfiguration {

    @Bean
    public IPFS ipfs(AhkenSettings settings) {
        return new IPFS(settings.getAhkenIpfsHost(), settings.getAhkenIpfsPort());
    }
}
