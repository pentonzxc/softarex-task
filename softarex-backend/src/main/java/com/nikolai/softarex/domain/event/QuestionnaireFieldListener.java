package com.nikolai.softarex.domain.event;


import com.nikolai.softarex.domain.interfaces.QuestionnaireResponseService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class QuestionnaireFieldListener {
    private final QuestionnaireResponseService service;

    public QuestionnaireFieldListener(QuestionnaireResponseService service) {
        this.service = service;
    }

    @EventListener
    @Async
    public void onUpdate(RemoveAllResponsesEvent event) {
        service.removeAllByUserId(event.getUserId());
    }
}
