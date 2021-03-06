package com.sporticus.domain.repositories;

import com.sporticus.domain.entities.Event;
import com.sporticus.domain.entities.Group;
import com.sporticus.domain.interfaces.IEvent;
import com.sporticus.domain.interfaces.IGroup;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IRepositoryEvent extends PagingAndSortingRepository<Event, Long> {
    List<IEvent> findByOwnerId(Long id);
}