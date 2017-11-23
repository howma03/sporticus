package com.sporticus.web.services;

import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SseEngine {

	private static Logger logger = LogFactory.getLogger(SseEngine.class.getName());

	private static long TIMEOUT = 30000L;

	private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

	public SseEngine() {

	}

	public Map<Long, SseEmitter> getEmitters() {
		return emitters;
	}

	public SseEmitter getEmitterByUserId(long userId) {
		SseEmitter sseEmitter = emitters.get(userId);
		return sseEmitter;
	}

	public long getTimeout() {
		return TIMEOUT;
	}

	@PostConstruct
	private void init() {

	}
}