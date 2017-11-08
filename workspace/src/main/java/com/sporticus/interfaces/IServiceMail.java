package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IUser;

import java.util.Map;

public interface IServiceMail {

    /***
     * Send a simple email
     *
     * @param from
     * @param to
     * @param subject
     * @param body
     */
    void sendMail(String from, String to, String subject, String body);

    /**
     * Send a verification email to the user - the user will need to verify that they have received the email by
     * visiting the web site and providing their verification code.
     *
     * @param user
     */
    void sendVerificationEmail(final IUser user);

    /**
     * Send a verification email to a new user - the user has been invited to join a group
     *
     * @param user
     */
    void sendVerificationEmailForInvitation(final IUser user, final IUser inviter);

    /**
     * This is an email services that will generate a standard cloud email containing the password for the user.
     *
     * @param user
     */
    void sendPasswordResetEmail(final IUser user);

    void sendGenericEmailFromTemplate(IUser user, String inSubject,
                                      String inTemplateName, Map<String, Object> values);
}
