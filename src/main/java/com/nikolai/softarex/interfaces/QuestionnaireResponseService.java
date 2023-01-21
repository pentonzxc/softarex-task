package com.nikolai.softarex.interfaces;

import com.nikolai.softarex.model.QuestionnaireResponse;

import java.util.List;

public interface QuestionnaireResponseService {
    public List<QuestionnaireResponse> findAll();
    public void save(QuestionnaireResponse response);
}
