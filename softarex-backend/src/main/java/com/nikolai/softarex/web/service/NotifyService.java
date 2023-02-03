package com.nikolai.softarex.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotifyService {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotifyService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyUser(String email) {
        messagingTemplate.convertAndSendToUser(email, "/queue/update", "update");
    }
}
