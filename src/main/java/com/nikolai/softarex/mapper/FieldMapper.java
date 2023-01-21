package com.nikolai.softarex.mapper;

import com.nikolai.softarex.dto.QuestionnaireFieldDto;
import com.nikolai.softarex.model.QuestionnaireField;
import org.springframework.stereotype.Component;


@Component
public class FieldMapper implements EntityMapper<QuestionnaireField , QuestionnaireFieldDto> {

    @Override
    public QuestionnaireField convertDtoToEntity(QuestionnaireFieldDto dto) {
        var field = new QuestionnaireField();

        field.setId(dto.getId());
        field.setLabel(dto.getLabel());
        field.setType(dto.getType());
        field.setOptions(dto.getOptions());
        field.setActive(dto.isActive());
        field.setRequired(dto.isRequired());

        return field;
    }

    @Override
    public QuestionnaireFieldDto convertEntityToDto(QuestionnaireField entity) {
        var field = new QuestionnaireFieldDto();

        field.setId(entity.getId());
        field.setActive(entity.isActive());
        field.setRequired(entity.isRequired());
        field.setLabel(entity.getLabel());
        field.setType(entity.getType());
        field.setOptions(entity.getOptions());

        return field;
    }
}
