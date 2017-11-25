package com.sporticus.web.services.test;

import com.sporticus.domain.entities.GroupMember;
import com.sporticus.domain.entities.Organisation;
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
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
	public DemoDataLoader(final IServiceUser serviceUser,
                            final IServiceGroup serviceGroup,
                            final IRepositoryGroupMember repositoryGroupMember,
                            final IServiceOrganisation serviceOrganisation,
	                        final IServiceLadder serviceLadder) {
		this.serviceUser = serviceUser;
		this.serviceGroup = serviceGroup;
		this.serviceOrganisation = serviceOrganisation;
		this.serviceLadder = serviceLadder;
	}

	@PostConstruct
	private void init() {
		LOGGER.debug(() -> "Checking for demo data");
		// check for groups - if there are none then create one and make all users member of the new group
		List<IGroup> groupsLadder = serviceLadder.getLaddersForUser(null);
		if (groupsLadder.size() == 0) {
			this.load();
		}
	}


	public void load() {
		LOGGER.debug(() -> "Creating demo data");
		try {
			IOrganisation org;
			List<IUser> users = serviceUser.getAll();

			if (users.size() > 0) {

				List<IOrganisation> orgs = serviceOrganisation.readAllOrganisations();
				if (orgs.size() == 0) {
					org = new Organisation()
							.setEnabled(true)
							.setCreated(new Date())
							.setAddress("Somewhere town")
							.setName("Test Organisation")
							.setOwnerId(users.get(0).getId())
							.setDomain("domain.com");
					orgs.add(serviceOrganisation.createOrganisation(org));
				}

				org = orgs.get(0);

				// check for ladder groups

				if (serviceLadder.readLaddersGroups().size() > 0) {
					return;
				}

				// Create a ladder group

				IGroup group = serviceLadder.createLadder(null,
						"My First Ladder Group",
						"Example ladder competition",
						org);

				for (IUser user : users) {
					IGroupMember gm = new GroupMember()
							.setCreated(new Date())
							.setEnabled(true)
							.setAcceptedOrRejectedDate(new Date())
							.setGroupId(group.getId())
							.setUserId(user.getId())
							.setPermissions(Permission.WRITE)
							.setStatus(Status.Accepted);

					serviceGroup.createGroupMember(gm, null);
				}
			}

		} catch (Exception ex) {
			LOGGER.error(() -> "Failed to load demo data", ex);
		}
	}
}
