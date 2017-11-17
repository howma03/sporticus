package com.sporticus.domain.repositories;

import com.sporticus.domain.entities.Group;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IRepositoryGroup extends PagingAndSortingRepository<Group, Long> {
    List<Group> findByOwnerOrganisationId(Long id);
}