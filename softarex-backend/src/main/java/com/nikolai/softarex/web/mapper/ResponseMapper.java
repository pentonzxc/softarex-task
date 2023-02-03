package com.nikolai.softarex.web.mapper;

import com.nikolai.softarex.domain.entity.QuestionnaireResponse;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ResponseMapper
        implements EntityMapper<QuestionnaireResponse, List<Object>> {
    @Override
    public QuestionnaireResponse convertDtoToEntity(List<Object> dto) {
        var entity = new QuestionnaireResponse();
        entity.setData(dto);
        return entity;
    }

    @Override
    public List<Object> convertEntityToDto(QuestionnaireResponse entity) {
        return entity.getData();
    }
}
