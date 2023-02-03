package com.nikolai.softarex.domain.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class QuestionnaireFieldPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public QuestionnaireFieldPublisher(ApplicationEventPublisher publisher) {
        this.eventPublisher = publisher;
    }


    public void publishEvent(RemoveAllResponsesEvent event){
        eventPublisher.publishEvent(event);
    }

}
