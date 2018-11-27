package io.kauri.ahken.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures application context for Jackson specific beans.
 *
 * @author Craig Williams
 */
@Configuration("ahkenJacksonConfiguration")
public class JacksonConfiguration {

    @Bean
    ObjectMapper ahkenObjectMapper() {
        return new ObjectMapper();
    }
}
