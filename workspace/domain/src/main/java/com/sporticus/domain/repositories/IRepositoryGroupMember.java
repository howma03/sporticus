package com.sporticus.domain.repositories;

import com.sporticus.domain.entities.GroupMember;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IRepositoryGroupMember extends PagingAndSortingRepository<GroupMember, Long> {

    List<GroupMember> findByUserId(Long userId);

    List<GroupMember> findByGroupId(Long groupId);

    List<GroupMember> findByGroupIdAndUserId(Long groupId, Long userId);
}