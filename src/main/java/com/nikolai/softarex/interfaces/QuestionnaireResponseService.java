package com.nikolai.softarex.interfaces;

import com.nikolai.softarex.model.QuestionnaireResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionnaireResponseService {
    Page<QuestionnaireResponse> findByUserId(int user_id, Pageable pageable);

}
