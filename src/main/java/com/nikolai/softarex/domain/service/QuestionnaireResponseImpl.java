package com.nikolai.softarex.domain.service;

import com.nikolai.softarex.domain.entity.QuestionnaireResponse;
import com.nikolai.softarex.domain.interfaces.QuestionnaireResponseService;
import com.nikolai.softarex.domain.repository.QuestionnaireResponseRepository;
import jakarta.transaction.Transactional;
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
    public Page<QuestionnaireResponse> findByUserId(int userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable);
    }

    @Override
    @Transactional
    public void removeAllByUserId(int userId) {
        repository.removeAllByUserId(userId);
    }
}
