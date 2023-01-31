package com.nikolai.softarex.service;


import com.nikolai.softarex.dto.ChangePasswordDto;
import com.nikolai.softarex.entity.User;
import com.nikolai.softarex.entity.UserPasswordChange;
import com.nikolai.softarex.exception.VerificationCodeNotFoundException;
import com.nikolai.softarex.repository.UserPasswordChangeRepository;
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


    private UserPasswordChangeRepository changePasswordRepository;

    private PasswordEncoder passwordEncoder;

    private EmailService emailService;


    @Autowired
    public ChangePasswordService(UserPasswordChangeRepository changePasswordRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.changePasswordRepository = changePasswordRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }



    @Transactional
    public void changePassword(User user, ChangePasswordDto passwords, HttpServletRequest emailRequest) throws MessagingException {
        if (!passwordEncoder.matches(passwords.getOldPassword(), user.getPassword())) {
            throw new BadCredentialsException("passwords doesn't match");
        }

        var verificationCode = RandomStringUtils.random(64);

        UserPasswordChange newPasswordChange = new UserPasswordChange();
        newPasswordChange.setPassword(passwordEncoder.encode(passwords.getNewPassword()));
        newPasswordChange.setVerificationCode(verificationCode);
        newPasswordChange.setUser(user);

        changePasswordRepository.save(newPasswordChange);

        emailService.sendUpdatePasswordEmail(verificationCode, user, emailRequest.getRequestURL().toString());
    }


    @Transactional
    public UserPasswordChange  verifyChangePassword(String verificationCode){
        Optional<UserPasswordChange> userPasswordChangeOpt = changePasswordRepository.findByVerificationCode(verificationCode);
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
