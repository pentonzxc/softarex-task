package com.nikolai.softarex.service;

import com.nikolai.softarex.interfaces.QuestionnaireResponseService;
import com.nikolai.softarex.model.QuestionnaireResponse;
import com.nikolai.softarex.repository.QuestionnaireResponseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionnaireResponseImpl implements QuestionnaireResponseService {
    private QuestionnaireResponseRepository repository;

    @Override
    public List<QuestionnaireResponse> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(QuestionnaireResponse response) {
        repository.save(response);
    }
}
