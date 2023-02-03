package com.nikolai.softarex.domain.converter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Converter(autoApply = true)
public class JsonArrayConverter implements AttributeConverter<List<Object>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Object> list) {
        String jsonInfo = null;
        try {
            jsonInfo = objectMapper.writeValueAsString(list);
        } catch (final JsonProcessingException e) {
            log.debug("JSON writing error", e);
        }

        return jsonInfo;
    }

    @Override
    public List<Object> convertToEntityAttribute(String dbData) {
        List<Object> list = null;
        try {
            list = objectMapper.readValue(dbData,
                    new TypeReference<ArrayList<Object>>() {
                    });
        } catch (final IOException e) {
            log.debug("JSON reading error", e);
        }

        return list;
    }
}
