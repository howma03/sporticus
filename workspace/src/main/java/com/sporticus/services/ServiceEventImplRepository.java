package com.sporticus.services;

import com.sporticus.domain.entities.Event;
import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.repositories.IRepositoryEvent;
import com.sporticus.interfaces.IServiceEvent;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceEventImplRepository  implements IServiceEvent {

	private static final Logger LOGGER = LogFactory.getLogger(ServiceEventImplRepository.class.getName());

	@Autowired
	private IServiceUser serviceUser;

	@Autowired
	private IRepositoryEvent repositoryEvent;

	public ServiceEventImplRepository() {
		LOGGER.debug(()->"Default CTOR");
	}

	@Override
	public IEvent create(IEvent event) {
		return repositoryEvent.save((Event)event);
	}

	@Override
	public IEvent findOne(Long id) {
		return repositoryEvent.findOne(id);
	}

	@Override
	public List<IEvent> findByOwnerId(Long userId) {
		return repositoryEvent.findByOwnerId(userId);
	}

	public void delete(Long eventId){
		if(eventId!=null){
			repositoryEvent.delete(eventId);
		}
	}
}
