package com.sporticus.web.services;

import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.interfaces.IServiceEvent;
import com.sporticus.interfaces.IServiceEvent.ServiceEventExceptionNotAllowed;
import com.sporticus.interfaces.IServiceEvent.ServiceEventExceptionNotFound;
import com.sporticus.services.dto.DtoEvent;
import com.sporticus.services.dto.DtoList;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/event")
public class RestControllerEvent extends ControllerAbstract {

    private final static Logger LOGGER = LogFactory.getLogger(RestControllerEvent.class.getName());

    private IServiceEvent serviceEvent;

    @Autowired
    public RestControllerEvent(final IServiceEvent serviceEvent) {
        this.serviceEvent = serviceEvent;
    }

    /**
     * Function to create an Event
     *
     * @param event
     * @return ResponseEntity<DtoEvent>
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody final DtoEvent event) {
        LOGGER.debug(() -> "Creating Event " + event.getName());
        try {
            return new ResponseEntity<>(new DtoEvent(serviceEvent.create(event, getLoggedInUser())), HttpStatus.OK);
        } catch (ServiceEventExceptionNotAllowed ex) {
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        }
    }

    /***
     * Function to delete an Event
     * @param id
     * @return success/failure
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOne(@PathVariable("id") final long id) {
        LOGGER.debug(() -> "Deleting Event with id " + id);
        try {
            serviceEvent.delete(id, getLoggedInUser());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServiceEventExceptionNotAllowed ex) {
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        } catch (ServiceEventExceptionNotFound ex) {
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Return an event given an identifier
     *
     * @param id
     * @return ResponseEntity<DtoEvent>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> read(@PathVariable("id") final long id) {
        LOGGER.debug(() -> "Reading Event with id " + id);
        try {
            IEvent found = serviceEvent.readEvent(id, getLoggedInUser());
            return new ResponseEntity<>(new DtoEvent(found), HttpStatus.OK);
        } catch (ServiceEventExceptionNotAllowed ex) {
            LOGGER.warn(() -> "Event not found - id=" + id);
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        } catch (ServiceEventExceptionNotFound ex) {
            LOGGER.warn(() -> "Event not found - id=" + id);
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Function to read all Future events for logged-in user
     *
     * @return ResponseEntity<DtoEvent>
     */
    @RequestMapping(value = "agenda", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoList<DtoEvent>> readAgenda() {
        final List<DtoEvent> list = this.serviceEvent.getAgenda(this.getLoggedInUserId(), getLoggedInUser())
                .stream()
                .map(e -> new DtoEvent(e)).collect(Collectors.toList());
        return new ResponseEntity<>(new DtoList<>(list), HttpStatus.OK);
    }

    /**
     * Function returns all events for the logged-in user
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DtoList<DtoEvent>> readAll() {
        final List<DtoEvent> list = this.serviceEvent.readAll(this.getLoggedInUserId(), this.getLoggedInUser())
                .stream()
                .map(e -> new DtoEvent(e)).collect(Collectors.toList());
        return new ResponseEntity<>(new DtoList<>(list), HttpStatus.OK);
    }

    /**
     * Function to update an Event
     * @param id
     * @param event
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<DtoEvent> update(@PathVariable("id") final long id,
                                           @RequestBody final DtoEvent event) {
        LOGGER.debug(() -> "Updating Event " + id);
        final IEvent found = serviceEvent.readEvent(id, getLoggedInUser());
        if(found == null) {
            LOGGER.warn(() -> "Event with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!this.getLoggedInUser().isAdmin() && !found.getOwnerId().equals(getLoggedInUserId())) {
            LOGGER.error(() -> "Events can only be updated by owner or system admins");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (!this.getLoggedInUser().isAdmin()) {
            event.setOwnerId(found.getOwnerId()); // we dont allow the event to be assigned to someone (unless Admin)
        }
        IEvent.COPY(event, found);
        final IEvent updated = serviceEvent.update(event, getLoggedInUser());
        LOGGER.info(() -> "Updated Event with id " + id);
        return new ResponseEntity<>(new DtoEvent(updated), HttpStatus.OK);
    }
}