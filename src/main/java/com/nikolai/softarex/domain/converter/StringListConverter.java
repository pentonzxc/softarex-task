package com.nikolai.softarex.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Converter(autoApply = true)
public class StringListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        try {
            return String.join(";", attribute);
        } catch (NullPointerException exception) {
            return "";
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(";"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
