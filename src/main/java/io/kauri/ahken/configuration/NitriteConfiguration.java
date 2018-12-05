package io.kauri.ahken.configuration;

import io.kauri.ahken.AhkenSettings;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.dizitart.no2.Nitrite;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NitriteConfiguration {

    private static final String DB_FILE_NAME = "db.dat";

    @Bean
    public Nitrite nitrite(AhkenSettings settings) throws IOException {
        final Path dbLocation = Paths.get(settings.getDbLocation());

        if (!Files.exists(dbLocation)) {
            Files.createDirectories(dbLocation);
        }

        return Nitrite.builder()
                .compressed()
                .filePath(settings.getDbLocation() + "/" + DB_FILE_NAME)
                .openOrCreate();
    }
}
