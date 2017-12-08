package com.sporticus.services;

import com.sporticus.domain.entities.GroupMember;
import com.sporticus.domain.entities.User;
import com.sporticus.domain.interfaces.*;
import com.sporticus.domain.repositories.IRepositoryUser;
import com.sporticus.interfaces.IServiceGroup;
import com.sporticus.interfaces.IServiceRelationship;
import com.sporticus.interfaces.IServiceUser;
import com.sporticus.services.dto.DtoGroup;
import com.sporticus.services.dto.DtoGroupMember;
import com.sporticus.services.dto.DtoList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.function.Predicate;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ServiceUserImplRepositoryListTest {

	@Autowired
	private IServiceUser serviceUser;

	// We Autowired the beans so they are injected from the configuration
	@Autowired
	private IRepositoryUser repositoryUser;

	@After
	public void after() {
	}

	@Test
	public void createUserTest() {

		assertEquals(serviceUser.getUserCount(), 10);

		IUser user = serviceUser.addUser(createTestUser(11));

		assertEquals(serviceUser.getUserCount(), 11);
	}

	@Test
	public void findUserTest() {
		assertEquals("First6", serviceUser.findUserByEmail("test6@test.com").getFirstName());
		assertEquals("Last9", serviceUser.findUserByEmail("test9@test.com").getLastName());
		assertEquals(serviceUser.getUserCount(), 10);
		assertEquals(serviceUser.getAllUserEmailAddresses().size(), 10);
	}

	@Before
	public void setup() {
		for (int i = 0; i < 10; i++) {
			repositoryUser.save(createTestUser(i));
		}
	}

	private User createTestUser(int i) {
		return (User) new User()
				.setId(new Long(i))
				.setEmail(String.format("test%d@test.com", i))
				.setFirstName(String.format("First%d", i))
				.setLastName(String.format("Last%d", i));
	}

	@Configuration
	static class AccountServiceTestContextConfiguration {
		@Bean
		public IRepositoryUser repositoryUser() {
			return new RepositoryUserImplArrayList();
		}

		@Bean
		public IServiceUser serviceUser() {
			return new ServiceUserImplRepository();
		}

		@Bean
		public IServiceRelationship serviceRelationship() {
			return new ServiceRelationshipImplRepository();
		}

		@Bean
		public IServiceGroup serviceGroup() {
			return new IServiceGroup() {
				@Override
				public IGroupMember acceptInvitation(IUser actor, Long id) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return null;
				}

				@Override
				public DtoGroupMember convertToDtoGroupMember(IGroupMember gm) {
					return null;
				}

				@Override
				public DtoList<DtoGroupMember> convertToDtoGroupMembers(List<IGroupMember> list) {
					return null;
				}

				@Override
				public DtoGroup convertToDtoGroup(IUser actor, IGroup group) {
					return null;
				}

				@Override
				public IGroup createGroup(IUser actor, IOrganisation organisation, IGroup group) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return null;
				}

				@Override
				public IEvent createGroupEvent(IUser actor, long groupId, IEvent event) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return null;
				}

				@Override
				public void deleteGroup(IUser actor, Long id) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {

				}

				@Override
				public IGroupMember createGroupMember(IUser actor, IGroupMember groupMember, IUser inviter) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return null;
				}

				@Override
				public IGroupMember createGroupMember(IUser actor, IGroup Group, IUser user, IGroupMember.Permission permissions, IUser inviter) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return null;
				}

				@Override
				public IGroupMember declineInvitation(IUser actor, Long id) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return null;
				}

				@Override
				public void deleteGroupMember(IUser actor, Long groupMemberId) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {

				}

				@Override
				public void deleteGroupMembershipsForUser(IUser actor, Long userId) {

				}

				@Override
				public List<IGroupMember> getGroupMembershipsForGroup(IUser actor, Long groupId) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return null;
				}

				@Override
				public List<IGroupMember> getGroupMembershipsForUser(IUser actor, Long userId) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return null;
				}

				@Override
				public IGroupMember getGroupMembershipsForUser(IUser actor, Long userId, Long groupId) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return null;
				}

				@Override
				public IOrganisation getGroupOwner(IUser actor, Long groupId) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return null;
				}

				@Override
				public IGroupMember getMembership(IUser actor, Long groupMembershipId) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return null;
				}

				@Override
				public List<IGroup> getMembershipGroupsForUser(IUser actor, Long userId) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return null;
				}

				@Override
				public List<IUser> getMembershipUsersForGroup(IUser actor, Long groupId, Predicate<IGroupMember> filter) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return null;
				}

				@Override
				public boolean isActiveGroupAdmin(IUser actor, Long groupId, IUser loggedInUser) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return false;
				}

				@Override
				public boolean isAllowedAccess(IUser actor, IUser user, IGroup group) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return false;
				}

				@Override
				public List<IGroup> readAllGroups(IUser actor) throws ServiceGroupExceptionNotAllowed {
					return null;
				}

				@Override
				public IGroup readGroup(IUser actor, Long groupId) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return null;
				}

				@Override
				public List<IUser> readGroupAdmins(IUser actor, long groupId) throws ServiceGroupExceptionNotAllowed {
					return null;
				}

				@Override
				public List<IUser> readGroupAdminsActive(IUser actor, long groupId) throws ServiceGroupExceptionNotAllowed {
					return null;
				}

				@Override
				public List<IEvent> readGroupEvents(IUser actor, long groupId) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return null;
				}

				@Override
				public List<IGroup> readGroups(IUser actor, IOrganisation organisation) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return null;
				}

				@Override
				public List<IGroup> readGroups(IUser actor, IGroupMember.IGroupMemberFilter groupMemberFilter) throws ServiceGroupExceptionNotAllowed {
					return null;
				}

				@Override
				public List<IGroup> readGroupsManagedByUser(IUser actor, Long userId) throws ServiceGroupExceptionNotAllowed {
					return null;
				}

				@Override
				public IGroupMember resendInvitation(IUser actor, Long id) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return null;
				}

				@Override
				public void setGroupOwnerOrganisation(IUser actor, Long groupId, IOrganisation organisation) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {

				}

				@Override
				public IGroup updateGroup(IUser actor, IGroup group) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return null;
				}

				@Override
				public IGroupMember updateGroupMember(IUser actor, GroupMember groupMember) throws ServiceGroupExceptionNotAllowed, ServiceGroupExceptionNotFound {
					return null;
				}
			};
		}

	}
}
