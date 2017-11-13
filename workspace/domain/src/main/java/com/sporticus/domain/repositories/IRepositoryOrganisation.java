package com.sporticus.domain.repositories;

import com.sporticus.domain.entities.Organisation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IRepositoryOrganisation extends PagingAndSortingRepository<Organisation, Long> {
    List<Organisation> findByOwnerId(Long ownerId);
}