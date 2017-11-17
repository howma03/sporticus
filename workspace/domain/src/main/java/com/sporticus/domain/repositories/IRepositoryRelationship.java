package com.sporticus.domain.repositories;

import com.sporticus.domain.entities.Event;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IRepositoryRelationship extends PagingAndSortingRepository<Event, Long> {
    List<Event> findByOwnerId(Long id);
}