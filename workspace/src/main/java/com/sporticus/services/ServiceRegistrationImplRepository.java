package com.sporticus.services;

import com.sporticus.domain.entities.User;
import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryUser;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceMail;
import com.sporticus.interfaces.IServicePasswordGenerator;
import com.sporticus.interfaces.IServiceRegistration;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

@Service("registrationService")
public class ServiceRegistrationImplRepository implements IServiceRegistration {

    private static final Logger LOGGER = LogFactory.getLogger(ServiceRegistrationImplRepository.class.getName());

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private IServiceUser userService;

    @Autowired
    private IRepositoryUser userRepository;

    @Autowired
    @Qualifier("serviceMailJava")
    private IServiceMail mailService;

    @Autowired
    private IServicePasswordGenerator passwordGenerator;

    @Autowired
    private IServiceGroup serviceGroup;

    public ServiceRegistrationImplRepository() {

    }

    @Override
    public IUser register(final IUser userIn) throws ExceptionRegistrationFailure {

        final IUser foundUser = userService.findUserByEmail(userIn.getEmail());
        if(foundUser != null) {
            throw new ExceptionRegistrationFailure("Already Registered ");
        }

        IUser newUser = null;
        try {
            newUser = new User(userIn);

            newUser.setPassword(ENCODER.encode(userIn.getPassword()));

            newUser = userService.addUser(newUser);

            mailService.sendVerificationEmail(newUser);

        } catch(InvocationTargetException | IllegalAccessException | RuntimeException e) {
            newUser.setEnabled(false);
            userService.updateUser(newUser);
            throw new ExceptionRegistrationFailure("Registration failed", e);
        }
        return newUser;
    }

    @Override
    public IUser registerWithInvitation(final IUser userIn, final IUser inviter, final IGroup group) throws ExceptionRegistrationFailure {

        final IUser foundUser = userService.findUserByEmail(userIn.getEmail());
        if(foundUser != null) {
            throw new ExceptionRegistrationFailure("Already Registered ");
        }

        IUser newUser = null;
        try {
            newUser = new User(userIn);

            newUser.setPassword(ENCODER.encode(userIn.getPassword()));

            newUser = userService.addUser(newUser);

            mailService.sendVerificationEmailForInvitation(newUser, inviter, group);

        } catch(InvocationTargetException | IllegalAccessException | RuntimeException e) {
            newUser.setEnabled(false);
            userService.updateUser(newUser);
            throw new ExceptionRegistrationFailure("Registration failed", e);
        }
        return newUser;
    }

    @Override
    public boolean completeUserVerification(final String emailAddress,
                                            final UUID verificationCode,
                                            final boolean decline) {
        final IUser user = userService.findUserByEmail(emailAddress);
        if(user == null) {
            LOGGER.warn(() -> String.format("Cannot locate user for verification process - email=[%s]", emailAddress));
            return false;
        }

        if(!verificationCode.toString().equals(user.getVerificationCode())) {
            LOGGER.warn(() -> String.format("Provided verification code [%s] does not match user's [%s] code [%s]",
                    verificationCode, user.getEmail(), user.getVerificationCode()));
            return false;
        }

        if(decline) {
            LOGGER.info(() -> "Registration - invitation has been declined - user=" + emailAddress);
            user.setVerified(true);
            user.setEnabled(false);

            serviceGroup.getGroupMembershipsForUser(user.getId()).forEach(i -> {
                serviceGroup.declineInvitation(i.getId());
                LOGGER.info(() -> "Registration - group invitation has been declined - invitationId=" + i.getId());
            });

        } else {
            LOGGER.info(() -> "Registration - invitation has been accepted - user=" + emailAddress);
            serviceGroup.getGroupMembershipsForUser(user.getId()).forEach(i -> {
                serviceGroup.acceptInvitation(i.getId());
                LOGGER.info(() -> "Registration - group invitation has been accepted - invitationId=" + i.getId());
            });

            user.setVerified(true);
            user.setEnabled(true);
        }
        userService.updateUser(user);

        return true;
    }

    @Override
    public boolean resetPassword(final String emailAddress) {
        final IUser user = this.userService.findUserByEmail(emailAddress);

        if(user == null) {
            LOGGER.warn(() -> "Cannot reset password; user not found - " + emailAddress);
            return false;
        }

        /**
         * We now need to reset the password - we need to generate a strong password.
         */
        user.setPassword(ENCODER.encode(passwordGenerator.generate()));
        userRepository.save((User) user);
        mailService.sendPasswordResetEmail(user);

        return true;
    }

    @Override
    public boolean requestVerificationCode(final String emailAddress) {
        final IUser user = this.userService.findUserByEmail(emailAddress);

        if(user == null) {
            LOGGER.warn(() -> "Cannot send verification code reminder; user not found - " + emailAddress);
            return false;
        }

        mailService.sendVerificationEmail(user);

        return true;
    }
}
