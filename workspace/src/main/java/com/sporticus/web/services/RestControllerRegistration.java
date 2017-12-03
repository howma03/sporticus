package com.sporticus.web.services;

import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceJobScheduler;
import com.sporticus.interfaces.IServiceOrganisation;
import com.sporticus.interfaces.IServiceRecapcha;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.services.dto.DtoUser;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import com.sporticus.web.controllers.ControllerAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/papi/registration/user")
public class RestControllerRegistration extends ControllerAbstract {

    private static final Logger LOGGER = LogFactory.getLogger(RestControllerRegistration.class.getName());

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private final IServiceUser serviceUser;
    private final IServiceOrganisation serviceOrganisation;
    private final IServiceJobScheduler serviceScheduler;
    private final IServiceRecapcha serviceRecaptcha;

    @Autowired
    public RestControllerRegistration(final IServiceUser serviceUser,
                                      final IServiceRecapcha serviceRecaptcha,
                                      final IServiceOrganisation serviceOrganisation,
                                      final IServiceJobScheduler serviceScheduler) {
        this.serviceUser = serviceUser;
        this.serviceRecaptcha = serviceRecaptcha;
        this.serviceOrganisation = serviceOrganisation;
        this.serviceScheduler = serviceScheduler;
    }

    /**
     * Function to copy properties to the DTO - we do not send the password
     *
     * @param user
     * @return dtoUser
     */
    private DtoUser getDtoUser(final IUser user) {
        final DtoUser dtoUser = new DtoUser(user);
        dtoUser.setPassword("");
        return dtoUser;
    }


    /**
     * Function to create a user
     *
     * @param user
     * @return DtoUser
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody final DtoUser user) {

        try {
            serviceRecaptcha.verify(user.getCaptchaResponse());
        } catch (Exception ex) {
            String message = "User failed reCAPTCHA";
            LOGGER.warn(() -> message);
            return new ResponseEntity<>(new RuntimeException(message, ex), HttpStatus.UNAUTHORIZED);
        }

        if (user.getFirstName().length() == 0) {
            String message = "First Name must be provided";
            LOGGER.warn(() -> message);
            return new ResponseEntity<>(new RuntimeException(message), HttpStatus.NOT_ACCEPTABLE);
        }
        if (user.getLastName().length() == 0) {
            String message = "Last Name must be provided";
            LOGGER.warn(() -> message);
            return new ResponseEntity<>(new RuntimeException(message), HttpStatus.NOT_ACCEPTABLE);
        }
        if (user.getEmail().length() == 0) {
            String message = "Email must be provided";
            LOGGER.warn(() -> message);
            return new ResponseEntity<>(new RuntimeException(message), HttpStatus.NOT_ACCEPTABLE);
        }

        final IUser found = serviceUser.findUserByEmail(user.getEmail());
        if (found != null) {
            LOGGER.warn(() -> "User already registered - email=" + user.getEmail());
            return new ResponseEntity<>(HttpStatus.FOUND);
        }

        user.setPassword(ENCODER.encode(user.getPassword()));
        DtoUser result = getDtoUser(serviceUser.addUser(user));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
