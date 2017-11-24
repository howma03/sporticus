package com.sporticus.web.services;

import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember;
import com.sporticus.domain.interfaces.INotification;
import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryOrganisation;
import com.sporticus.interfaces.IServiceGroup;

import com.sporticus.interfaces.IServiceNotification;
import com.sporticus.interfaces.IServiceNotification.ServiceNotificationExceptionNotAllowed;
import com.sporticus.interfaces.IServiceNotification.ServiceNotificationExceptionNotFound;
import com.sporticus.services.dto.DtoEventLadder;
import com.sporticus.services.dto.DtoGroup;
import com.sporticus.services.dto.DtoList;
import com.sporticus.services.dto.DtoNotification;
import com.sporticus.services.dto.DtoOrganisation;
import com.sporticus.services.dto.DtoUser;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import com.sporticus.web.controllers.ControllerAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notification")
public class RestControllerNotification extends ControllerAbstract {

    private final static Logger LOGGER = LogFactory.getLogger(RestControllerNotification.class.getName());

    private IServiceNotification serviceNotification;

    @Autowired
    public RestControllerNotification(final IServiceNotification serviceNotification) {
        this.serviceNotification = serviceNotification;
    }

    /**
     * Function to create an Notification
     *
     * @param notification
     * @return ResponseEntity<DtoNotification>
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoNotification> create(@RequestBody final DtoNotification notification) {

        if(notification.getOwnerId() == null) {
            LOGGER.warn(() -> "As the notification has no owner, this sets the logged in user to be the owner");
            notification.setOwnerId(this.getLoggedInUserId());
        }

        LOGGER.debug(() -> "Creating Notification " + notification.getText());
        //RestControllerPushImplSse.sendEventToOne(this.getLoggedInUserId(), notification);
        return new ResponseEntity<>(new DtoNotification(serviceNotification.createNotification(notification)), HttpStatus.OK);
    }

    /**
     * Function to return all of the notifications for the logged-in user
     *
     * @return ResponseEntity<DtoNotification>
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoList<DtoNotification>> readAll() {
        return new ResponseEntity<>(new DtoList(serviceNotification.findAllOwnedBy(this.getLoggedInUserId()).stream().map(n->new DtoNotification(n)).collect(Collectors.toList())), HttpStatus.OK);
    }

    /**
     * Function returns a notification given an identifier
     *
     * @param id
     * @return ResponseEntity<DtoNotification>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoNotification> read(@PathVariable("id") final long id) {
        if(id == -1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<INotification> found = serviceNotification.findOne(id);
        if(!found.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.debug(() -> "Reading Notification with id " + id);
        return new ResponseEntity<>(new DtoNotification(found.get()), HttpStatus.OK);
    }

    /**
     * Function to update a notification
     *
     * @param id
     * @param notification
     * @return ResponseEntity<DtoNotification>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") final long id,
                                    @RequestBody final DtoNotification notification) {
        try {
            return new ResponseEntity<>(serviceNotification.updateNotification(getLoggedInUserId(), notification), HttpStatus.OK);
        } catch (ServiceNotificationExceptionNotFound ex) {
            LOGGER.warn(() -> "Failed to update challenge", ex);
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        } catch (ServiceNotificationExceptionNotAllowed ex) {
            LOGGER.warn(() -> "Failed to update challenge", ex);
            return new ResponseEntity<>(ex, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Function to delete a notification -
     * Only the owner of the notification or an administrators can perform this operation
     *
     * @param id
     * @return ResponseEntity<DtoUser>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOne(@PathVariable("id") final long id) {
        final Optional<INotification> found = serviceNotification.findOne(id);
        if (!found.isPresent()) {
            LOGGER.warn(() -> "Notification with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        INotification notification = found.get();
        if (!this.getLoggedInUser().isAdmin() || !notification.getOwnerId().equals(this.getLoggedInUserId())) {
            LOGGER.error(() -> "Only the owner or a system administrator can delete a notification");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        LOGGER.debug(() -> "Deleting Notification with id " + id);
        serviceNotification.deleteNotification(notification.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}