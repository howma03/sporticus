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

@RestController
public class RestControllerPushImplSse extends ControllerAbstract {

	private final static Logger LOGGER = LogFactory.getLogger(RestControllerPushImplSse.class.getName());

	@Autowired
	private static SseEngine sseEngine;

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

			this.send(emitter, new Notification()
					.setOwnerId(this.getLoggedInUserId())
					.setTitle("Test")
					.setType(TYPE.APPLICATION)
					.setText("Something happened"));
		}
		return new ResponseEntity<>(emitter, HttpStatus.OK);
	}


	public RestControllerPushImplSse() {

	}

	public static void sendEventAll() {
	}

	public static void sendEventToOne(Long loggedInUserId, INotification notification) {
		SseEmitter emitterByUserId = sseEngine.getEmitterByUserId(loggedInUserId);
		if(emitterByUserId != null) {
			send(emitterByUserId, notification);
		}
	}


	private static void send(SseEmitter emitter, INotification notification) {
		new Thread(() -> {
			try {
				Thread.sleep(1000 * 10);
					emitter.send("Hello");
				emitter.complete();
			} catch (IOException | InterruptedException ex) {
				LOGGER.warn(() -> "Failed to push notification", ex);
			}
		}).start();
	}
}
