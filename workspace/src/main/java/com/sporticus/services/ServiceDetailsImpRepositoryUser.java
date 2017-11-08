package com.sporticus.services;


import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryUser;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class ServiceDetailsImpRepositoryUser implements org.springframework.security.core.userdetails.UserDetailsService {

	private static final Logger LOGGER = LogFactory.getLogger(ServiceDetailsImpRepositoryUser.class.getName());

	@Autowired
	private IRepositoryUser userRepository;

	private List<GrantedAuthority> buildUserAuthority(final List<String> roles) {
		final Set<GrantedAuthority> setAuthorities = new HashSet<>();
		for(String role : roles) {
			setAuthorities.add(new SimpleGrantedAuthority(role));
		}
		return new ArrayList<>(setAuthorities);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		LOGGER.info(() -> String.format("Checking for email '%s'", email));

		final IUser myUser = userRepository.findByEmail(email.toLowerCase().trim());
		if(myUser == null) {
			throw new UsernameNotFoundException("User not found");
		}

		// If the user is a group manager or an organisation owner then they will receive addition

		List<String> roles = new ArrayList<>();
		roles.add("ROLE_USER");

		if(myUser.isAdmin()) {
			LOGGER.info(() -> String.format("\tUser is admin - email='%s'", email));
			roles.add("ROLE_ADMIN");
		}

		final List<GrantedAuthority> authorities = buildUserAuthority(roles);

		return buildUserForAuthentication(myUser, authorities);
	}

	private User buildUserForAuthentication(final IUser user,
	                                        final List<GrantedAuthority> authorities) {
		LOGGER.info(() -> "User authentication - " + user.getEmail() + ":" + user.getPassword());

		return new User(user.getEmail(),
				user.getPassword(),
				user.isEnabled(),
				true,
				true,
				true,
				authorities);
	}
}
