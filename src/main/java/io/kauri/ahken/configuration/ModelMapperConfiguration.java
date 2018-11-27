package io.kauri.ahken.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kauri.ahken.AhkenException;
import java.io.IOException;
import net.consensys.eventeum.dto.event.parameter.EventParameter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures application context for Model Mapper specific beans.
 *
 * @author Craig Williams
 */
@Configuration
public class ModelMapperConfiguration {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public ModelMapper ahkenModelMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new EventParamConverter());

        return modelMapper;
    }

    private class EventParamConverter
            implements Converter<EventParameter, io.kauri.ahken.model.event.parameter.EventParameter> {

        @Override
        public io.kauri.ahken.model.event.parameter.EventParameter convert(
                MappingContext<EventParameter, io.kauri.ahken.model.event.parameter.EventParameter> context) {

            try {
                final String json = objectMapper.writeValueAsString(context.getSource());

                return objectMapper.readValue(json, io.kauri.ahken.model.event.parameter.EventParameter.class);
            } catch (IOException e) {
                throw new AhkenException("Unable to convert", e);
            }
        }
    }
}
