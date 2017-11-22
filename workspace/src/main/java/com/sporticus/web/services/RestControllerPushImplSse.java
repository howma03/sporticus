package com.sporticus.web.services;

import com.sporticus.domain.entities.Notification;
import com.sporticus.domain.interfaces.INotification;
import com.sporticus.web.controllers.ControllerAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Controller
public class RestControllerPushImplSse extends ControllerAbstract {

	@Autowired
	private SseEngine sseEngine;

	public RestControllerPushImplSse() {

	}

	@GetMapping("/api/notification/feed")
	public SseEmitter getResults() {
		if (!sseEngine.getEmitters().containsKey(getLoggedInUserId())) {
			sseEngine.getEmitters().put(getLoggedInUserId(), new SseEmitter(30000L));
		}
		SseEmitter emitter = sseEngine.getEmitters().get(getLoggedInUserId());
		this.send(emitter, new Notification()
				.setOwnerId(this.getLoggedInUserId())
				.setTitle("Test")
				.setText("Something happened"));
		return emitter;
	}

	@Async
	public void send(SseEmitter emitter, INotification notication) {
		try {
			emitter.send(notication);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
