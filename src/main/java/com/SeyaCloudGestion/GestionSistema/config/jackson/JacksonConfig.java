package com.SeyaCloudGestion.GestionSistema.config.jackson;

import com.SeyaCloudGestion.GestionSistema.config.jackson.configs.EliminarEspaciosInicioFinal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule stringModule = new SimpleModule();
        stringModule.addDeserializer(String.class, new EliminarEspaciosInicioFinal());

        mapper.registerModule(stringModule);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }
}
