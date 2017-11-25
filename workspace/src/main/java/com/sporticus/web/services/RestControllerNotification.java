package com.sporticus.web.services;

import com.sporticus.domain.interfaces.INotification;
import com.sporticus.interfaces.IServiceEvent.ServiceEventExceptionNotAllowed;
import com.sporticus.interfaces.IServiceEvent.ServiceEventExceptionNotFound;
import com.sporticus.interfaces.IServiceNotification;
import com.sporticus.interfaces.IServiceNotification.ServiceNotificationExceptionNotAllowed;
import com.sporticus.interfaces.IServiceNotification.ServiceNotificationExceptionNotFound;
import com.sporticus.services.dto.DtoList;
import com.sporticus.services.dto.DtoNotification;
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
        LOGGER.debug(() -> "Creating Notification " + notification.getText());
	    return new ResponseEntity<>(new DtoNotification(serviceNotification.createNotification(getLoggedInUser(), notification)), HttpStatus.OK);
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
	 * Function to delete a notification -
	 * Only the owner of the notification or an administrators can perform this operation
	 *
	 * @param id
	 * @return HttpStatus
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteOne(@PathVariable("id") final long id) {
		try {
			serviceNotification.deleteNotification(getLoggedInUser(), id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ServiceNotificationExceptionNotAllowed ex) {
			LOGGER.warn(() -> "Delete not allowed - id=" + id);
			return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
		} catch (ServiceNotificationExceptionNotFound ex) {
			LOGGER.warn(() -> "Failed to delete, not found - id=" + id);
			return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
		}
	}

    /**
     * Function returns a notification given an identifier
     *
     * @param id
     * @return ResponseEntity<DtoNotification>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> read(@PathVariable("id") final long id) {
        LOGGER.debug(() -> "Reading Notification with id " + id);

	    try {
		    INotification found = serviceNotification.findOne(getLoggedInUser(), id);
		    return new ResponseEntity<>(new DtoNotification(found), HttpStatus.OK);
	    } catch (ServiceEventExceptionNotAllowed ex) {
		    LOGGER.warn(() -> "Read not allowed - id=" + id);
		    return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
	    } catch (ServiceEventExceptionNotFound ex) {
		    LOGGER.warn(() -> "Not found - id=" + id);
		    return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
	    }
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
	        return new ResponseEntity<>(serviceNotification.updateNotification(getLoggedInUser(), notification), HttpStatus.OK);
        } catch (ServiceNotificationExceptionNotFound ex) {
	        LOGGER.warn(() -> "Failed to update", ex);
	        return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        } catch (ServiceNotificationExceptionNotAllowed ex) {
	        LOGGER.warn(() -> "Failed to update", ex);
	        return new ResponseEntity<>(ex, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}