package com.sporticus.services;

import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.interfaces.IServiceMail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service(value = "ServiceMailImplDummy")
@Qualifier("test")
public class ServiceMailImplDummy implements IServiceMail {
	@Override
	public void sendMail(String from, String to, String subject, String body) {

	}

	@Override
	public void sendVerificationEmail(IUser user) {

	}

	@Override
	public void sendVerificationEmailForInvitation(IUser user, IUser inviter, IGroup group) {

	}

	@Override
	public void sendPasswordResetEmail(IUser user) {

	}

	@Override
	public void sendGenericEmailFromTemplate(IUser user, String inSubject, String inTemplateName, Map<String, Object> values) {

	}
}
