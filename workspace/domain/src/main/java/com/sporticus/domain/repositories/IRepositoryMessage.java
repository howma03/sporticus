package com.sporticus.domain.repositories;

import com.sporticus.domain.entities.Message;
import com.sporticus.domain.interfaces.IMessage;
import com.sporticus.domain.interfaces.IMessage.STATUS;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IRepositoryMessage extends PagingAndSortingRepository<Message, Long> {
	List<IMessage> findByRecipientId(Long id);

	List<IMessage> findByRecipientIdAndStatus(Long id, STATUS status);

	List<IMessage> findBySenderId(Long id);
}
