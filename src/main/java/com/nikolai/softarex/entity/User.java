package com.nikolai.softarex.entity;


import jakarta.persistence.*;
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


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionnaireField> questionnaireFields;

    @OneToMany(mappedBy = "user", cascade =
            {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private List<QuestionnaireResponse> questionnaireResponses;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private UserPasswordChange passwordChange;


    public void addQuestionnaireField(QuestionnaireField field) {
        questionnaireFields.add(field);
        field.setUser(this);
    }

    public void removeQuestionnaireField(QuestionnaireField field) {
        questionnaireFields.remove(field);
        field.setUser(null);
    }


    public void setPasswordChange(UserPasswordChange passwordChange) {
        if (passwordChange == null) {
            if (this.passwordChange != null) {
                this.passwordChange.setUser(this);
            }
        } else {
            passwordChange.setUser(this);
        }
        this.passwordChange = passwordChange;
    }

    public void addQuestionnaireResponse(QuestionnaireResponse response) {
        questionnaireResponses.add(response);
        response.setUser(this);
    }

    public List<QuestionnaireResponse> getQuestionnaireResponses() {
        return questionnaireResponses;
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


    public String getFullName() {
        return firstName + " " + lastName;
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


    public List<QuestionnaireField> getQuestionnaireFields() {
        return questionnaireFields;
    }

    public UserPasswordChange getPasswordChange() {
        return passwordChange;
    }

}
