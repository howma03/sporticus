package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IUser;

import java.util.List;

public interface IServiceEvent {

	IEvent create(IEvent event);

	IEvent findOne(Long id);

	List<IEvent> findByOwnerId(Long userId);

	void delete(Long eventId);

	default void delete(IEvent event) {

	}

	List<IEvent> getAgenda(Long actorUser);

	IEvent readEvent(long id, IUser actorUser);
}
