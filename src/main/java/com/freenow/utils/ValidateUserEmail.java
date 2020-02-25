package com.freenow.utils;

import org.apache.commons.validator.routines.EmailValidator;


/**
 * @author Ragul Dhandapani
 *
 */
public class ValidateUserEmail {

    /**
     * This method is used to validate the email address its valid format or not.
     *
     * @param emailId
     * @return boolean
     * @throws Exception
     */
    public static boolean verifyEmailId(String emailId) throws Exception{

        EmailValidator emailValidator = EmailValidator.getInstance();
        boolean isEmailValid = emailValidator.isValid (emailId);
        String validationResult = isEmailValid ? "Valid":"Invalid";
        System.out.println ("The Email Address "+emailId+" of user is "+validationResult);
        return isEmailValid;
    }
}
