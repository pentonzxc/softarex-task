package com.nikolai.softarex.web.service;

import com.nikolai.softarex.domain.entity.User;
import com.nikolai.softarex.web.util.EmailMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String sender;

    public void sendUpdatePasswordEmail(String verificationCode,
                                        User user,
                                        String requestURL) throws MessagingException {
        String message = EmailMessage.updatePasswordMessage + "\n"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>";

        String verifyURL = requestURL + "/verify?code=" + verificationCode;

        message = message.replace("[[URL]]", verifyURL);

        sendEmail(EmailDetails.builder()
                .receiver(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .message(message)
                .subject(EmailMessage.updatePasswordSubject)
                .build());
    }

    public void sendVerificationEmail(User user, String requestURL) throws MessagingException {

        String message = EmailMessage.registrationMessage + "\n"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>";

        String verifyURL = requestURL + "/verify?code=" + user.getVerificationCode();

        message = message.replace("[[URL]]", verifyURL);

        sendEmail(EmailDetails.builder()
                .receiver(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .message(message)
                .subject(EmailMessage.registrationSubject)
                .build());
    }

    private void sendEmail(EmailDetails emailDetails) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);


        helper.setFrom("soft_arex_task@gmail.com");
        helper.setTo(emailDetails.getReceiver());
        helper.setText(emailDetails.getMessage(), true);
        helper.setSubject(emailDetails.getSubject());

        mailSender.send(message);
    }

    @lombok.Value
    @Builder
    static class EmailDetails {
        String receiver;

        String firstName;

        String lastName;

        String message;

        String subject;
    }

}
