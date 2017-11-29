package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IUser;

import java.util.UUID;

public interface IServiceRegistration {

    class ExceptionRegistrationFailure extends RuntimeException {
        private static final long serialVersionUID = -6724234804657129123L;

        public ExceptionRegistrationFailure(final String message) {
            super(message);
        }

        public ExceptionRegistrationFailure(final String message, final Exception e) {
            super(message, e);
        }
    }

	/**
	 * The verification process is completed successfully if the provided email address matches a registered user and
	 * the verification code matches the once stored.
	 *
	 * @param emailAddress
	 * @param verificationCode
	 * @return boolean
	 */
	boolean completeUserVerification(IUser actor, String emailAddress, UUID verificationCode, boolean decline);

    /**
     * In order to register the user we first have validate their details, then generate a verification code before
     * sending a verification email (the registration process will be completed once the user receives the email and
     * visits the web site with their verification code).
     *
     * @param userIn
     */
    IUser register(IUser actor, final IUser userIn) throws ExceptionRegistrationFailure;

	IUser registerWithInvitation(IUser actor, final IUser userIn, final IUser inviter, final IGroup group) throws ExceptionRegistrationFailure;

    /**
     * An email containing the user's verification code is sent to the user
     *
     * @param emailAddress
     * @return boolean
     */
    boolean requestVerificationCode(IUser actor, String emailAddress);

    /**
     * The user's password is reset and an email is generated.
     *
     * @param emailAddress
     * @return boolean
     */
    boolean resetPassword(IUser actor, final String emailAddress);

}