package com.nikolai.softarex.web.service;

import com.nikolai.softarex.domain.entity.User;
import com.nikolai.softarex.domain.entity.UserPasswordChange;
import com.nikolai.softarex.domain.interfaces.UserChangePasswordService;
import com.nikolai.softarex.web.dto.ChangePasswordDto;
import com.nikolai.softarex.web.exception.VerificationCodeNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChangePasswordService {


    private final UserChangePasswordService changePasswordService;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;


    @Autowired
    public ChangePasswordService(
            UserChangePasswordService changePasswordService,
            PasswordEncoder passwordEncoder,
            EmailService emailService) {
        this.changePasswordService = changePasswordService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }


    @Transactional
    public void changePassword(User user, ChangePasswordDto passwords, HttpServletRequest emailRequest) throws MessagingException {
        if (!passwordEncoder.matches(passwords.getOldPassword(), user.getPassword())) {
            throw new BadCredentialsException("passwords doesn't match");
        }

        UserPasswordChange passwordChange = user.getPasswordChange() == null ?
                new UserPasswordChange() : user.getPasswordChange();


        var verificationCode = RandomStringUtils.random(64);
        passwordChange.setPassword(passwordEncoder.encode(passwords.getNewPassword()));
        passwordChange.setVerificationCode(verificationCode);
        passwordChange.setUser(user);

        changePasswordService.save(passwordChange);

        emailService.sendUpdatePasswordEmail(verificationCode, user, emailRequest.getRequestURL().toString());
    }


    @Transactional
    public UserPasswordChange verifyChangePassword(String verificationCode) {
        Optional<UserPasswordChange> userPasswordChangeOpt = changePasswordService.findByVerificationCode(verificationCode);
        UserPasswordChange userPasswordChange = userPasswordChangeOpt.orElseThrow(VerificationCodeNotFoundException::new);
        String password = userPasswordChange.getPassword();

        return userPasswordChange;
    }


    private UserPasswordChange updateUserPasswordChange(UserPasswordChange src, UserPasswordChange trg) {
        if (src == null) {
            return trg;
        }

        src.setVerificationCode(trg.getVerificationCode());
        src.setPassword(trg.getPassword());

        return src;
    }

}
