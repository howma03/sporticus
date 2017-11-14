package com.sporticus.web.services;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/papi/user")
public class RestControllerAuth extends ControllerAbstract {

    private static final Logger LOGGER = LogFactory.getLogger(RestControllerAuth.class.getName());

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private UserDetailsService serviceDetails;

    @Autowired
    public RestControllerAuth(final UserDetailsService serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    @RequestMapping(value = "/authenticate/{username}/{password}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity authenticateGet(@PathVariable("username") String userName,
                                               @PathVariable("password") String password) {

        LOGGER.debug(() -> "GET:/papi/user/authenticate");

        final UserDetails userDetails = serviceDetails.loadUserByUsername(userName);

        if(!ENCODER.matches(password, userDetails.getPassword())){

            LOGGER.info(()->"Passwords do not match");

            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // TODO: actually perform the authentication so that we get a token

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/authenticate/{username}/{password}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity authenticate(@PathVariable("username") String userName,
                                               @PathVariable("password") String password) {

        LOGGER.debug(() -> "POST:/papi/user/authenticate");

        final UserDetails userDetails = serviceDetails.loadUserByUsername(userName);

        if(!ENCODER.matches(password, userDetails.getPassword())){

            LOGGER.info(()->"Passwords do not match");

            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
