package com.sporticus.web.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@Controller
public class RestControllerPushImplSse {

	@GetMapping("/feed")
	public SseEmitter getResults() {

		String eventId = UUID.randomUUID().toString();
		SseEmitter emitter = new SseEmitter(30000L);
		// sseEngine.getEmitters().put(eventId, emitter);

		return emitter;
	}

}
