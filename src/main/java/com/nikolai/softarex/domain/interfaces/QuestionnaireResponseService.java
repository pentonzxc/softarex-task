package com.nikolai.softarex.domain.interfaces;

import com.nikolai.softarex.domain.entity.QuestionnaireResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionnaireResponseService {
    Page<QuestionnaireResponse> findByUserId(int userId, Pageable pageable);

    void removeAllByUserId(int userId);

}
