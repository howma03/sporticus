package com.sporticus.web.services;

import com.sporticus.domain.entities.Notification;
import com.sporticus.domain.interfaces.INotification;
import com.sporticus.domain.interfaces.INotification.TYPE;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import com.sporticus.web.controllers.ControllerAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Controller
public class RestControllerPushImplSse extends ControllerAbstract {

	private final static Logger LOGGER = LogFactory.getLogger(RestControllerPushImplSse.class.getName());

	@Autowired
	private SseEngine sseEngine;

	@GetMapping("/api/notification/feed")
	public ResponseEntity<SseEmitter> getResults() {

		if(sseEngine==null){
			LOGGER.warn(()->"No SSE Engine available");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		final Long fLoggedInUserId = getLoggedInUserId();
		SseEmitter emitter = sseEngine.getEmitters().get(fLoggedInUserId);
		if (emitter == null) {
			LOGGER.debug(() -> "Creating new emitter");
			SseEmitter fEmitter = emitter = new SseEmitter();

			emitter.onCompletion(() -> {
				LOGGER.debug(() -> "Emitter completed");
				sseEngine.getEmitters().remove(fLoggedInUserId);
			});
			emitter.onTimeout(() -> {
				fEmitter.complete();
			});

			sseEngine.getEmitters().put(fLoggedInUserId, emitter);
		}
		return new ResponseEntity<>(emitter, HttpStatus.OK);
	}


	public RestControllerPushImplSse() {

	}

	private  void send(SseEmitter emitter, INotification notification) {
		new Thread(() -> {
			try {
				//TODO - Convert to JSON.
				emitter.send(notification);
				emitter.complete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
