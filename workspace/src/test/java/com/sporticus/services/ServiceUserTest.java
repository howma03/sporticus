package com.sporticus.services;

import com.sporticus.domain.entities.User;
import com.sporticus.domain.interfaces.IUser;
import com.sporticus.domain.repositories.IRepositoryUser;
import com.sporticus.interfaces.IServiceUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Iterator;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ServiceUserTest {

	@Autowired
	private IServiceUser serviceUser;

	// We Autowired the beans so they are injected from the configuration
	@Autowired
	private IRepositoryUser repositoryUser;

	public static <T> void mockIterable(Iterable<T> iterable, T... values) {
		Iterator<T> mockIterator = mock(Iterator.class);
		when(iterable.iterator()).thenReturn(mockIterator);

		if (values.length == 0) {
			when(mockIterator.hasNext()).thenReturn(false);
			return;
		} else if (values.length == 1) {
			when(mockIterator.hasNext()).thenReturn(true, false);
			when(mockIterator.next()).thenReturn(values[0]);
		} else {
			// build boolean array for hasNext()
			Boolean[] hasNextResponses = new Boolean[values.length];
			for (int i = 0; i < hasNextResponses.length - 1; i++) {
				hasNextResponses[i] = true;
			}
			hasNextResponses[hasNextResponses.length - 1] = false;
			when(mockIterator.hasNext()).thenReturn(true, hasNextResponses);
			T[] valuesMinusTheFirst = Arrays.copyOfRange(values, 1, values.length);
			when(mockIterator.next()).thenReturn(values[0], valuesMinusTheFirst);
		}
	}

	@After
	public void after() {
		// This is allowed here: using container injected mocks
		Mockito.reset(repositoryUser);
	}

	@Test
	public void createUserTest() {

		assertEquals(serviceUser.getUserCount(), 10);

		IUser user = serviceUser.addUser(createTestUser(11));

		when(repositoryUser.count()).thenReturn(new Long(11));

		assertEquals(serviceUser.getUserCount(), 11);

		Mockito.verify(repositoryUser,
				VerificationModeFactory.times(1))
				.findByEmail(Mockito.anyString());
	}

	@Test
	public void findUserTest() {
		assertEquals("First6", serviceUser.findUserByEmail("test6@test.com").getFirstName());
		assertEquals("Last9", serviceUser.findUserByEmail("test9@test.com").getLastName());
		assertEquals(serviceUser.getUserCount(), 10);

		// assertEquals(serviceUser.getAllUserEmailAddresses().size(), 10);

		Mockito.verify(repositoryUser,
				VerificationModeFactory.times(2))
				.findByEmail(Mockito.anyString());
	}

	@Before
	public void setup() {

		User[] users = new User[10];
		{
			for (int i = 0; i < 10; i++) {
				User u = createTestUser(i);
				when(repositoryUser.findByEmail(u.getEmail())).thenReturn(u);
				when(repositoryUser.findOne(u.getId())).thenReturn(u);
				users[i] = u;
			}
		}

		when(repositoryUser.count()).thenReturn(new Long(users.length));
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
			return mock(IRepositoryUser.class);
		}

		@Bean
		public IServiceUser serviceUser() {
			return new ServiceUserImplRepository();
		}
	}
}
