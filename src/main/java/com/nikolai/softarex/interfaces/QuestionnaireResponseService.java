package com.nikolai.softarex.interfaces;

import com.nikolai.softarex.entity.QuestionnaireResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionnaireResponseService {
    Page<QuestionnaireResponse> findByUserId(int user_id, Pageable pageable);

}
