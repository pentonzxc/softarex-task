package com.nikolai.softarex.service;

import com.nikolai.softarex.dto.QuestionnaireDto;
import com.nikolai.softarex.interfaces.QuestionnaireService;
import com.nikolai.softarex.interfaces.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {
    private final UserService userService;

    @Autowired
    public QuestionnaireServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public Optional<QuestionnaireDto> findById(Integer id) {
        var userOpt = userService.findById(id);
        return userOpt.map(user -> new QuestionnaireDto(user.getQuestionnaireFields()));
    }
}
