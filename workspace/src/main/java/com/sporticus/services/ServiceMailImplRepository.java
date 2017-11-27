package com.sporticus.services;

import com.sporticus.domain.entities.Message;
import com.sporticus.domain.interfaces.IMessage;
import com.sporticus.domain.interfaces.IMessage.IMPORTANCE;
import com.sporticus.domain.interfaces.IMessage.STATUS;
import com.sporticus.domain.interfaces.IMessage.TYPE;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryMessage;
import com.sporticus.interfaces.IServiceMail;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service("serviceMailImplRepository")
public class ServiceMailImplRepository extends ServiceMailAbstract implements IServiceMail {

	private static final Logger LOGGER = LogFactory.getLogger(ServiceMailImplRepository.class.getName());

	@Autowired
	private IRepositoryMessage repositoryMessage;

	@Autowired
	private IServiceUser serviceUser;

	public ServiceMailImplRepository() {
		LOGGER.debug(() -> "Default CTOR");
	}

	@PostConstruct
	public void init() {

	}

	/**
	 * Function simply stores the email in the repository
	 *
	 * @param from
	 * @param to
	 * @param inSubject
	 * @param inBody
	 * @throws RuntimeException
	 */
	@Override
	protected void sendEmail(String from, String to, String inSubject, String inBody) throws RuntimeException {

		LOGGER.info(() -> String.format("Sending message - from=[%s], to=[%s]", from, to));

		IUser sender = serviceUser.findUserByEmail(from);
		IUser recipient = serviceUser.findUserByEmail(to);

		IMessage message = new Message();
		message.setType(TYPE.EMAIL)
				.setImportance(IMPORTANCE.NORMAL)
				.setStatus(STATUS.UNREAD)
				.setSubject(inSubject)
				.setBody(inBody)
				.setSenderId(sender.getId())
				.setRecipientId(recipient.getId());

		repositoryMessage.save((Message) message);
	}

}
