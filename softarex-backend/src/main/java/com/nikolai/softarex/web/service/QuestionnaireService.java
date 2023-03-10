package com.nikolai.softarex.web.service;

import com.nikolai.softarex.domain.entity.QuestionnaireField;
import com.nikolai.softarex.domain.entity.QuestionnaireResponse;
import com.nikolai.softarex.domain.interfaces.UserService;
import com.nikolai.softarex.web.dto.QuestionnaireAuthorDto;
import com.nikolai.softarex.web.dto.QuestionnaireDto;
import com.nikolai.softarex.web.exception.QuestionnaireNotFoundException;
import com.nikolai.softarex.web.exception.UserNotFoundException;
import com.nikolai.softarex.web.util.ExceptionMessageUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionnaireService {
    private final UserService userService;

    private final NotifyService notifyService;

    @Autowired
    public QuestionnaireService(UserService userService, NotifyService notifyService) {
        this.userService = userService;
        this.notifyService = notifyService;
    }

    @Transactional
    public QuestionnaireDto findFromUserId(Integer userId) {
        var userOpt = userService.findById(userId);
        return userOpt.map(user -> new QuestionnaireDto(
                        user.getQuestionnaireFields()
                                .stream()
                                .filter(QuestionnaireField::isActive)
                                .toList()))
                .orElseThrow(() -> new QuestionnaireNotFoundException(
                        ExceptionMessageUtil.questionnaireNotFound(userId)));
    }

    public void createQuestionnaireResponse(Integer userId, QuestionnaireResponse response) {
        var user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        user.addQuestionnaireResponse(response);
        userService.save(user);

        notifyService.notifyUser(user.getEmail());
    }

    public List<QuestionnaireAuthorDto> findAll() {
        return userService.findAll().stream().map(
                user -> new QuestionnaireAuthorDto(user.getId(), user.getFullName())
        ).toList();
    }


}
