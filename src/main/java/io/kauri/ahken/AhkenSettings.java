package io.kauri.ahken;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class AhkenSettings {

    @Value("${ahken.scripts.location}")
    private String scriptsLocation;

    @Value("${ahken.defaultIdentifier}")
    private String defaultUserIdentifier;

    @Value("${ahken.ipfs.host}")
    private String ahkenIpfsHost;

    @Value("${ahken.ipfs.port}")
    private Integer ahkenIpfsPort;

    @Value("${ahken.db.location}")
    private String dbLocation;
}
