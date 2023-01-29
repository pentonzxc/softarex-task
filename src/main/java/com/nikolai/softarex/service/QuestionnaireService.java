package com.nikolai.softarex.service;

import com.nikolai.softarex.dto.QuestionnaireDto;
import com.nikolai.softarex.entity.QuestionnaireResponse;
import com.nikolai.softarex.exception.QuestionnaireNotFoundException;
import com.nikolai.softarex.exception.UserNotFoundException;
import com.nikolai.softarex.interfaces.UserService;
import com.nikolai.softarex.util.ExceptionMessageUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public QuestionnaireDto questionnaireFromUserId(Integer userId) {
        var userOpt = userService.findById(userId);
        return userOpt.map(user -> new QuestionnaireDto(user.getQuestionnaireFields()))
                .orElseThrow(() -> new QuestionnaireNotFoundException(
                        ExceptionMessageUtil.questionnaireNotFound(userId)));
    }

    public void createQuestionnaireResponse(Integer userId, QuestionnaireResponse response) {
        var user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        user.addQuestionnaireResponse(response);
        userService.save(user);

        notifyService.notifyUser(user.getEmail());
    }


}
