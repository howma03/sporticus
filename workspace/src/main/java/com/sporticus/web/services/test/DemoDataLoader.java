package com.sporticus.web.services.test;

import com.sporticus.domain.entities.GroupMember;
import com.sporticus.domain.entities.Organisation;
import com.sporticus.domain.entities.User;
import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember;
import com.sporticus.domain.interfaces.IGroupMember.Permission;
import com.sporticus.domain.interfaces.IGroupMember.Status;
import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryGroupMember;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceLadder;
import com.sporticus.interfaces.IServiceOrganisation;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DemoDataLoader {

	private static final Logger LOGGER = LogFactory.getLogger(DemoDataLoader.class.getName());

	protected IServiceUser serviceUser;

	private final IServiceGroup serviceGroup;

	private final IServiceOrganisation serviceOrganisation;

	private final IServiceLadder serviceLadder;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	public DemoDataLoader(final IServiceUser serviceUser,
	                      final IServiceGroup serviceGroup,
	                      final IServiceOrganisation serviceOrganisation,
	                      final IServiceLadder serviceLadder) {
		this.serviceUser = serviceUser;
		this.serviceGroup = serviceGroup;
		this.serviceOrganisation = serviceOrganisation;
		this.serviceLadder = serviceLadder;
	}

	private IUser actor = new User() {
		public Boolean isAdmin() {
			return true;
		}
	};

	@PostConstruct
	private void init() {
		LOGGER.debug(() -> "Checking for demo data");

		LOGGER.debug(() -> "Checking users");
		List<IUser> users = serviceUser.getAll();
		if (users.size() == 0) {
			users.addAll(generateUsers());
		}

		LOGGER.debug(() -> "Check for (ladder) groups - if there are none then create one and make each user a member of the new group");
		List<IGroup> groupsLadder = serviceLadder.readLaddersGroups(actor);
		if (groupsLadder.size() < 2) {
			groupsLadder.addAll(this.generateLadders());
		} else {
			// Ensure all users are members of the first ladder
			IGroup ladder = groupsLadder.get(0);
			List<IGroupMember> members = serviceGroup.getGroupMembershipsForGroup(actor, ladder.getId());
			for (IUser user : users) {
				if (members.stream().noneMatch(gm -> gm.getUserId().equals(user.getId()))) {
					IGroupMember gm = new GroupMember()
							.setCreated(new Date())
							.setEnabled(true)
							.setAcceptedOrRejectedDate(new Date())
							.setGroupId(ladder.getId())
							.setUserId(user.getId())
							.setPermissions(Permission.WRITE)
							.setStatus(Status.Accepted);
					serviceGroup.createGroupMember(actor, gm, users.get(0));
				}
			}
		}
	}

	public List<IUser> generateUsers() {
		List<IUser> users = serviceUser.getAll();
		LOGGER.debug(() -> "Adding some users");

		addUser(users, new User()
				.setAdmin(false)
				.setFirstName("Test")
				.setLastName("Test")
				.setEmail("test@sporticus.com")
				.setPassword(passwordEncoder.encode("S0uthern"))
				.setVerified(true)
				.setAdmin(true)
				.setEnabled(true));
		addUser(users, new User()
				.setAdmin(false)
				.setFirstName("Angela")
				.setLastName("Alright")
				.setEmail("angela@sporticus.com")
				.setPassword(passwordEncoder.encode("S0uthern"))
				.setVerified(true)
				.setEnabled(true));
		addUser(users, new User()
				.setAdmin(false)
				.setFirstName("Ben")
				.setLastName("Boring")
				.setEmail("ben@sporticus.com")
				.setPassword(passwordEncoder.encode("S0uthern"))
				.setVerified(true)
				.setEnabled(true));
		addUser(users, new User()
				.setAdmin(false)
				.setFirstName("Colin")
				.setLastName("Cool")
				.setEmail("colin@sporticus.com")
				.setPassword(passwordEncoder.encode("S0uthern"))
				.setVerified(true)
				.setEnabled(true));
		addUser(users, new User()
				.setAdmin(false)
				.setFirstName("Martin")
				.setLastName("Manchester")
				.setEmail("martin@sporticus.com")
				.setPassword(passwordEncoder.encode("S0uthern"))
				.setVerified(true)
				.setEnabled(true));

		return users;
	}

	public List<IGroup> generateLadders() {
		List<IGroup> list = new ArrayList<>();
		LOGGER.debug(() -> "Creating demo data");
		try {
			IOrganisation org;
			List<IUser> users = serviceUser.getAll();

			if (users.size() > 0) {
				List<IOrganisation> orgs = serviceOrganisation.readAllOrganisations(actor);
				if (orgs.size() == 0) {
					org = new Organisation()
							.setEnabled(true)
							.setCreated(new Date())
							.setOwnerId(actor.getId())
							.setAddress("Somewhere town")
							.setName("Test Organisation")
							.setOwnerId(users.get(0).getId())
							.setDomain("domain.com");
					orgs.add(serviceOrganisation.createOrganisation(actor, org));
				}

				org = orgs.get(0);

				// Create a ladder group

				for (int i = 0; i < 2; i++) {
					IGroup group = serviceLadder.createLadder(actor,
							"My Ladder Group " + i,
							"Example ladder competition",
							org);

					list.add(group);

					for (IUser user : users) {
						IGroupMember gm = new GroupMember()
								.setCreated(new Date())
								.setEnabled(true)
								.setAcceptedOrRejectedDate(new Date())
								.setGroupId(group.getId())
								.setUserId(user.getId())
								.setPermissions(Permission.WRITE)
								.setStatus(Status.Accepted);

						serviceGroup.createGroupMember(actor, gm, null);
					}
				}
			}

		} catch (Exception ex) {
			LOGGER.error(() -> "Failed to generateLadders demo data", ex);
		}
		return list;
	}

	private void addUser(List<IUser> list, IUser user) {
		try {
			IUser newUser = serviceUser.addUser(user);
			list.add(newUser);
		} catch (RuntimeException ex) {
			// Ignore
		}
	}
}
