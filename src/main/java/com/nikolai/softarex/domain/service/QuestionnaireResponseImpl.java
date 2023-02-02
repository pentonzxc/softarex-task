package com.nikolai.softarex.domain.service;

import com.nikolai.softarex.domain.entity.QuestionnaireResponse;
import com.nikolai.softarex.domain.interfaces.QuestionnaireResponseService;
import com.nikolai.softarex.domain.repository.QuestionnaireResponseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireResponseImpl implements QuestionnaireResponseService {
    private final QuestionnaireResponseRepository repository;

    public QuestionnaireResponseImpl(QuestionnaireResponseRepository repository) {
        this.repository = repository;
    }


    @Override
    public Page<QuestionnaireResponse> findByUserId(int user_id, Pageable pageable) {
        return repository.findByUserId(user_id, pageable);
    }
}
