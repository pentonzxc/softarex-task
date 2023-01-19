package com.nikolai.softarex.converter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Converter(autoApply = true)
public class JsonConverter implements AttributeConverter<Map<String, Object>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> map) {
        String jsonInfo = null;
        try {
            jsonInfo = objectMapper.writeValueAsString(map);
        } catch (final JsonProcessingException e) {
            log.debug("JSON writing error", e);
        }

        return jsonInfo;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        Map<String, Object> map = null;
        try {
            map = objectMapper.readValue(dbData,
                    new TypeReference<HashMap<String, Object>>() {
                    });
        } catch (final IOException e) {
            log.debug("JSON reading error", e);
        }

        return map;
    }
}
