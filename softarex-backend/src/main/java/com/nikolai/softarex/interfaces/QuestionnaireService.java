package com.nikolai.softarex.interfaces;

import com.nikolai.softarex.dto.QuestionnaireDto;

import java.util.Optional;

public interface QuestionnaireService {
    Optional<QuestionnaireDto> findById(Integer id);
}
