package com.sporticus.services;

import com.sporticus.domain.entities.User;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryUser;
import com.sporticus.interfaces.IServiceUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
	}
}
