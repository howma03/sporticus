package com.sporticus.domain.repositories;

import com.sporticus.domain.entities.Group;
import com.sporticus.domain.entities.Notification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IRepositoryNotification extends PagingAndSortingRepository<Notification, Long> {
}