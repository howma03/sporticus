package com.sporticus.interfaces;

import com.sporticus.domain.interfaces.IEvent;

import java.util.List;

public interface IServiceEvent {

	IEvent create(IEvent event);

	IEvent findOne(Long id);

	List<IEvent> findByOwnerId(Long userId);

	default void delete(IEvent event) {

	}


}
