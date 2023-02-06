package uk.co.ryanharrison.crudapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import uk.co.ryanharrison.crudapi.util.JsonUtils;

@Configuration
public class Config {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return JsonUtils.MAPPER;
    }

}
