package com.nikolai.softarex.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessageUtil {

    public static String emailNotFoundMsg(String email) {
        return String.format("User with email %s - not found", email);
    }

    public static String userAlreadyExistMsg(String email) {
        return String.format("User with email %s -  already exist", email);
    }

    public static String userNotFoundMsg(Integer id) {
        return String.format("User with id %d -  already exist", id);
    }

    public static String questionnaireNotFound(Integer id) {
        return String.format("Questionnaire with id %d - not found", id);
    }


}
