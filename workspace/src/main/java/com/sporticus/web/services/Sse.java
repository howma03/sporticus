package com.sporticus.web.services;

import com.sporticus.domain.interfaces.INotification;

public interface Sse {

	/**
	 * This method does the necessary actions to publish the event with the proper data
	 *
	 * @param eventData
	 */
	void handle(INotification eventData);

	/**
	 * Push data to SSE channel associated to an event ID
	 *
	 * @param eventId
	 * @param data
	 */
	void push(String eventId, Object data);

}