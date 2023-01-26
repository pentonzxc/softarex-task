package com.nikolai.softarex.mapper;

import com.nikolai.softarex.dto.QuestionnaireFieldDto;
import com.nikolai.softarex.model.QuestionnaireField;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
public class FieldMapper implements EntityMapper<QuestionnaireField, QuestionnaireFieldDto> {

    @Override
    public QuestionnaireField convertDtoToEntity(QuestionnaireFieldDto dto) {
        var entity = new QuestionnaireField();

        entity.setId(dto.getId());
        entity.setLabel(dto.getLabel());
        entity.setType(dto.getType());
        entity.setOptions(
                Arrays.stream(dto.getOptions().split("\n")).toList()
        );
        entity.setActive(dto.isActive());
        entity.setRequired(dto.isRequired());

        return entity;
    }

    @Override
    public QuestionnaireFieldDto convertEntityToDto(QuestionnaireField entity) {
        var dto = new QuestionnaireFieldDto();

        dto.setId(entity.getId());
        dto.setActive(entity.isActive());
        dto.setRequired(entity.isRequired());
        dto.setLabel(entity.getLabel());
        dto.setType(entity.getType());
        dto.setOptions(entity.getOptions().stream().reduce(
                (f, s) -> f.concat("\n").concat(s)
        ).orElse(""));

        return dto;
    }
}
