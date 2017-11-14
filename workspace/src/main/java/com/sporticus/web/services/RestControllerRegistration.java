package com.sporticus.web.services;

import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceJobScheduler;
import com.sporticus.interfaces.IServiceOrganisation;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.services.dto.DtoList;
import com.sporticus.services.dto.DtoUser;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import com.sporticus.web.controllers.ControllerAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/papi/registration/user")
public class RestControllerRegistration extends ControllerAbstract {

    private static final Logger LOGGER = LogFactory.getLogger(RestControllerRegistration.class.getName());

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private final IServiceUser serviceUser;
    private final IServiceOrganisation serviceOrganisation;
    private final IServiceJobScheduler serviceScheduler;

    @Autowired
    public RestControllerRegistration(final IServiceUser serviceUser,
                                      final IServiceOrganisation serviceOrganisation,
                                      final IServiceJobScheduler serviceScheduler) {
        this.serviceUser = serviceUser;
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
        // Now determine the number of groups the user is a member of
        dtoUser.setPassword(null);
        return dtoUser;
    }

    /**
     * Function to create a user
     *
     * @param user
     * @return DtoUser
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoUser> create(@RequestBody final DtoUser user) {
        final IUser found = serviceUser.findUserByEmail(user.getEmail());
        if (found != null) {
            LOGGER.warn(() -> "User already registered - email=" + user.getEmail());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setPassword(ENCODER.encode(user.getPassword()));
        return new ResponseEntity<>(getDtoUser(serviceUser.addUser(user)), HttpStatus.OK);
    }
}
