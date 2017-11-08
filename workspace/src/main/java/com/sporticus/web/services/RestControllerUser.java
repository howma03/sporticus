package com.sporticus.web.services;

import com.sporticus.domain.entities.User;
import com.sporticus.domain.repositories.IRepositoryUser;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import com.sporticus.web.controllers.ControllerAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/papi/user")
public class RestControllerUser extends ControllerAbstract {

    private static final Logger LOGGER = LogFactory.getLogger(RestControllerUser.class.getName());

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private UserDetailsService serviceDetails;

    @Autowired
    public RestControllerUser(final UserDetailsService serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    @RequestMapping(value = "/authenticate/{username}/{password}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticate(@PathVariable("username") String userName,
                                               @PathVariable("password") String password) {

        LOGGER.debug(() -> "GET:/papi/user/authenticate");

        final UserDetails userDetails = serviceDetails.loadUserByUsername(userName);

        if(!userDetails.getPassword().equalsIgnoreCase(ENCODER.encode(password))){

            LOGGER.info(()->"Passwords do not match");

            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
