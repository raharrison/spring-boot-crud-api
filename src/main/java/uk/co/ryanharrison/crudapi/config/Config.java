package uk.co.ryanharrison.crudapi.config;

import org.jspecify.annotations.NullMarked;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import tools.jackson.databind.json.JsonMapper;
import uk.co.ryanharrison.crudapi.util.JsonUtils;

@NullMarked
@Configuration
public class Config {

    @Bean
    @Primary
    public JsonMapper jsonMapper() {
        return JsonUtils.MAPPER;
    }

}
