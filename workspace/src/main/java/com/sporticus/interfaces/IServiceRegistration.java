package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IUser;

import java.util.UUID;

public interface IServiceRegistration {

    public class ExceptionRegistrationFailure extends RuntimeException {
        private static final long serialVersionUID = -6724234804657129123L;

        public ExceptionRegistrationFailure(final String message) {
            super(message);
        }

        public ExceptionRegistrationFailure(final String message, final Exception e) {
            super(message, e);
        }
    }

    /**
     * In order to register the user we first have validate their details, then generate a verification code before
     * sending a verification email (the registration process will be completed once the user receives the email and
     * visits the web site with their verification code).
     *
     * @param userIn
     */
    IUser register(final IUser userIn) throws ExceptionRegistrationFailure;

    IUser registerWithInvitation(final IUser userIn, final IUser inviter, final IGroup group) throws ExceptionRegistrationFailure;

    /**
     * The verification process is completed successfully if the provided email address matches a registered user and
     * the verification code matches the once stored.
     *
     * @param emailAddress
     * @param verificationCode
     * @return boolean
     */
    boolean completeUserVerification(String emailAddress, UUID verificationCode, boolean decline);

    /**
     * The user's password is reset and an email is generated.
     *
     * @param emailAddress
     * @return boolean
     */
    boolean resetPassword(final String emailAddress);

    /**
     * An email containing the user's verification code is sent to the user
     *
     * @param emailAddress
     * @return boolean
     */
    boolean requestVerificationCode(String emailAddress);

}