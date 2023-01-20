package com.nikolai.softarex.model;


import com.sun.mail.imap.protocol.BODY;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(schema = "softarex_task")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Boolean isActive;

    private String verificationCode;

    public void setPasswordChange(String passwordChange) {
        this.passwordChange = passwordChange;
    }

    @Transient
    private String passwordChange;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionnaireField> questionnaireFields;


    public void addQuestionnaireField(QuestionnaireField field) {
        questionnaireFields.add(field);
        field.setUser(this);
    }

    public void removeQuestionnaireField(QuestionnaireField field) {
        questionnaireFields.remove(field);
        field.setUser(null);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.NO_AUTHORITIES;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public void setQuestionnaireFields(List<QuestionnaireField> questionnaireFields) {
        this.questionnaireFields = questionnaireFields;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Boolean isActive() {
        return isActive;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public String getPasswordChange() {
        return passwordChange;
    }

    public List<QuestionnaireField> getQuestionnaireFields() {
        return questionnaireFields;
    }
}
