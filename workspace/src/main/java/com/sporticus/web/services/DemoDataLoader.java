package com.sporticus.web.services;

import com.sporticus.domain.entities.Group;
import com.sporticus.domain.entities.GroupMember;
import com.sporticus.domain.entities.Organisation;
import com.sporticus.domain.interfaces.IGroup;
import com.sporticus.domain.interfaces.IGroupMember.Permission;
import com.sporticus.domain.interfaces.IGroupMember.Status;
import com.sporticus.domain.interfaces.IOrganisation;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryGroupMember;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceLadder;
import com.sporticus.interfaces.IServiceOrganisation;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.services.ServiceLadderImplRepository;
import com.sporticus.types.GroupType;
import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

	public void load() {
		try {
			IOrganisation org = null;
			List<IUser> users = serviceUser.getAll();
			if (users.size() > 0) {
				List<IOrganisation> orgs = serviceOrganisation.readAllOrganisations();
				if (orgs.size() == 0) {
					org = new Organisation();
					org.setEnabled(true);
					org.setCreated(new Date());
					org.setAddress("Somewhere town");
					org.setName("Test Organisation");
					org.setOwnerId(users.get(0).getId());
					org.setDomain("domain.com");
					org = serviceOrganisation.createOrganisation(org);
				} else {
					org = orgs.get(0);
				}

				// check for ladder groups

				if(serviceLadder.getLadders().size()>0){
					return;
				}

				// create a ladder group

				IGroup group = new Group();
				group.setType(GroupType.LADDER.toString());
				group.setName("Ladder Group");
				group.setEnabled(true);
				group.setDescription("A ladder group");
				group.setOwnerOrganisationId(org.getId());

				group = serviceGroup.createGroup(org, group);
				for (IUser user : users) {
					GroupMember gm = new GroupMember();

					gm.setCreated(new Date());
					gm.setEnabled(true);
					gm.setAcceptedOrRejectedDate(new Date());
					gm.setGroupId(group.getId());
					gm.setUserId(user.getId());
					gm.setPermissions(Permission.WRITE);
					gm.setStatus(Status.Accepted);

					serviceGroup.createGroupMember(gm, null);
				}
			}

		} catch (Exception ex) {
			LOGGER.error(() -> "Failed to load demo data", ex);
		}
	}
}
