package com.sporticus.web.services.admin;

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
@RequestMapping("/api/management/user")
public class RestControllerAdminUser extends ControllerAbstract {

    private static final Logger LOGGER = LogFactory.getLogger(RestControllerAdminUser.class.getName());

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private final IServiceUser serviceUser;
    private final IServiceOrganisation serviceOrganisation;
    private final IServiceJobScheduler serviceScheduler;

    @Autowired
    public RestControllerAdminUser(final IServiceUser serviceUser,
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
     * Only a system administrator can create an User
     *
     * @param user
     * @return DtoUser
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoUser> create(@RequestBody final DtoUser user) {
        LOGGER.debug(() -> "Creating User " + user.getEmail());
        if (!this.getLoggedInUser().isAdmin()) {
            LOGGER.error(() -> "Users can only be created by system administrators");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        user.setPassword(ENCODER.encode(user.getPassword()));
        return new ResponseEntity<>(getDtoUser(serviceUser.addUser(user)), HttpStatus.OK);
    }

    /**
     * Function to read all users
     *
     * @return ResponseEntity<DtoUsers>
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoList<DtoUser>> readAll() {
        if (!this.getLoggedInUser().isAdmin()) {
            LOGGER.error(() -> "Users can only be read by system administrators");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if(false) {
            ServletRequestAttributes attr = (ServletRequestAttributes)
                    RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true); // true == allow create
        }

        /**
         * As well as the standard user data we want to include statistics for number of users and groups
         * (as well as number of open invitations)
         */
        final DtoList list = new DtoList<DtoUser>();
        this.serviceUser.getAll().forEach(u -> {
            list.add(getDtoUser(u));
        });
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Function to read a user given an identifier
     *
     * @param id
     * @return ResponseEntity<DtoUser>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoUser> read(@PathVariable("id") final long id) {
        if (!this.getLoggedInUser().isAdmin()) {
            LOGGER.error(() -> "Users can only be read by system administrators");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        LOGGER.debug(() -> "Reading User with id " + id);
        final IUser found = serviceUser.findOne(id);
        if (found == null) {
            LOGGER.warn(() -> "User not found - id=" + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(getDtoUser(found), HttpStatus.OK);
    }

    /**
     * Function to update a user - only admins can update users
     *
     * @param id
     * @param user
     * @return ResponseEntity<DtoUser>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<DtoUser> update(@PathVariable("id") final long id, @RequestBody final DtoUser user) {

        //TODO: Ensure security handles this
        if (!this.getLoggedInUser().isAdmin()) {
            LOGGER.error(() -> "Users can only be updated by system administrators");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        IUser found = serviceUser.findOne(id);
        if (found == null) {
            LOGGER.warn(() -> "User with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.debug(() -> "Updating User " + id);
        if (user.getPassword() != null && user.getPassword().length() > 0) {
            user.setPassword(ENCODER.encode(user.getPassword()));
        }
        found = serviceUser.updateUser(user);
        LOGGER.info(() -> "Updated user with id " + id);
        return new ResponseEntity<>(getDtoUser(found), HttpStatus.OK);
    }

    /**
     * Function to delete a user -
     * Only the administrators can perform this operation
     *
     * @param id
     * @return ResponseEntity<DtoUser>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DtoUser> deleteOne(@PathVariable("id") final long id) {
        if (!this.getLoggedInUser().isAdmin()) {
            LOGGER.error(() -> "Users can only be deleted by system administrators");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        final IUser found = serviceUser.findOne(id);
        if (found == null) {
            LOGGER.warn(() -> "User with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LOGGER.debug(() -> "Deleting User with id " + id);
        serviceUser.deleteUser(found.getId());
        return new ResponseEntity<>(getDtoUser(found), HttpStatus.OK);
    }

}
