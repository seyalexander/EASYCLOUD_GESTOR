package com.SeyaCloudGestion.GestionSistema.config.jackson.configs;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class EliminarEspaciosInicioFinal extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser parser, DeserializationContext context) {
        try {
            String value = parser.getText();
            if (value == null) {
                return null;
            }
            String cleaned = value.trim().replaceAll("\\s{2,}", " ");
            return cleaned.isEmpty() ? null : cleaned;
        } catch (Exception e) {
            throw new RuntimeException("Error al deserializar String ", e);
        }
    }
}
