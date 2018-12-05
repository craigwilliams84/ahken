package io.kauri.ahken.configuration;

import io.kauri.ahken.AhkenSettings;
import org.dizitart.no2.Nitrite;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NitriteConfiguration {

    @Bean
    public Nitrite nitrite(AhkenSettings settings) {
        return Nitrite.builder()
                .compressed()
                .filePath(settings.getDbLocation())
                .openOrCreate();
    }
}
