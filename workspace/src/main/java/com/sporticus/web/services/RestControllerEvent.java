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
        return new ResponseEntity<>(new DtoEvent(serviceEvent.create(getLoggedInUser(), event)), HttpStatus.OK);
    }

    /**
     * Function to delete an event
     *
     * @param id
     * @return ResponseEntity<?>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") final long id) {
        try {
            serviceEvent.delete(getLoggedInUser(), id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServiceEventExceptionNotAllowed ex) {
            LOGGER.warn(() -> "Delete not allowed - id=" + id);
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        } catch (ServiceEventExceptionNotFound ex) {
            LOGGER.warn(() -> "Event not found - id=" + id);
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
            IEvent found = serviceEvent.readEvent(getLoggedInUser(), id);
            return new ResponseEntity<>(new DtoEvent(found), HttpStatus.OK);
        } catch (ServiceEventExceptionNotAllowed ex) {
            LOGGER.warn(() -> "Read not allowed - id=" + id);
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
    public ResponseEntity<?> readAgenda() {
        try {
            return new ResponseEntity<>(new DtoList<>(this.serviceEvent.getAgenda(getLoggedInUser(), getLoggedInUserId())
                    .stream()
                    .map(e -> new DtoEvent(e))
                    .collect(Collectors.toList())), HttpStatus.OK);
        } catch (ServiceEventExceptionNotAllowed ex) {
            LOGGER.warn(() -> "Agenda Read not allowed");
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        } catch (ServiceEventExceptionNotFound ex) {
            LOGGER.warn(() -> "Agenda not found");
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Function returns all events for the logged-in user
     * @return ResponseEntity<?>
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readAll() {
        return new ResponseEntity<>(new DtoList<>(this.serviceEvent.readAll(this.getLoggedInUser(), this.getLoggedInUserId())
                .stream()
                .map(e -> new DtoEvent(e))
                .collect(Collectors.toList())), HttpStatus.OK);
    }

    /**
     * Function to update an Event
     * @param id
     * @param event
     * @return ResponseEntity<?>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") final long id,
                                    @RequestBody final DtoEvent event) {
        try {
            final IEvent updated = serviceEvent.update(getLoggedInUser(), event);
            LOGGER.info(() -> "Updated Event with id " + id);
            return new ResponseEntity<>(new DtoEvent(updated), HttpStatus.OK);
        } catch (ServiceEventExceptionNotAllowed ex) {
            LOGGER.warn(() -> "Updated not allowed - id=" + id);
            return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
        } catch (ServiceEventExceptionNotFound ex) {
            LOGGER.warn(() -> "Updated failed - not found - id=" + id);
            return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
        }
    }
}