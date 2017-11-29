package com.sporticus.web.services;

import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SseEngine {

	private static Logger LOGGER = LogFactory.getLogger(SseEngine.class.getName());

	private static long TIMEOUT = 30000L;

	private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

	public SseEngine() {

	}

	public Map<Long, SseEmitter> getEmitters() {
		return emitters;
	}

	public SseEmitter getEmitterByUserId(long userId) {
		return emitters.get(userId);
	}

	public void send(long id, Object data) throws IOException {
		SseEmitter emitter = emitters.get(id);
		if (emitter == null) {
			LOGGER.warn(() -> "Failed to locate emitter - id=" + id);
		} else {
			emitter.send(data);
			LOGGER.info(() -> "Sent data via emitter - id=" + id);
		}
	}


	public long getTimeout() {
		return TIMEOUT;
	}

	@PostConstruct
	private void init() {

	}
}