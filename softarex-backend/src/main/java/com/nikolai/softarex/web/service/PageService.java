package com.nikolai.softarex.web.service;

import com.nikolai.softarex.domain.entity.QuestionnaireResponse;
import com.nikolai.softarex.domain.entity.QuestionnaireField;
import com.nikolai.softarex.domain.interfaces.QuestionnaireFieldService;
import com.nikolai.softarex.domain.interfaces.QuestionnaireResponseService;
import com.nikolai.softarex.web.dto.PageDto;
import com.nikolai.softarex.web.dto.QuestionnaireFieldDto;
import com.nikolai.softarex.web.mapper.EntityMapper;
import com.nikolai.softarex.web.util.PageUtil;
import org.springframework.stereotype.Service;

@Service
public class PageService {

    private final QuestionnaireFieldService fieldService;
    private final QuestionnaireResponseService responseService;
    private final EntityMapper<QuestionnaireField, QuestionnaireFieldDto> fieldMapper;


    public PageService(QuestionnaireFieldService fieldService,
                       QuestionnaireResponseService responseService,
                       EntityMapper<QuestionnaireField, QuestionnaireFieldDto> fieldMapper) {
        this.fieldService = fieldService;
        this.responseService = responseService;
        this.fieldMapper = fieldMapper;
    }


    public PageDto<?> createFieldPage(Integer page, Integer size, int userId) {
        var pageable = PageUtil.createPage(page, size);
        var contentWrapper = fieldService.findAllByUserId(userId, pageable);
        var fields = contentWrapper.stream().map(fieldMapper::convertEntityToDto).toList();

        return new PageDto<>(contentWrapper.getTotalPages(), contentWrapper.getTotalElements(), fields);
    }


    public PageDto<?> createResponsePage(Integer page, Integer size, int userId) {
        var pageable = PageUtil.createPage(page, size);
        var contentWrapper = responseService.findByUserId(userId, pageable);
        var responses = contentWrapper.stream().map(QuestionnaireResponse::getData).toList();

        return new PageDto<>(contentWrapper.getTotalPages(), contentWrapper.getTotalElements(), responses);
    }


}
