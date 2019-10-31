package org.kpmp.auth;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserAuthTest {

	private UserAuth testUserAuth;

	@Before
	public void setUp() throws Exception {
		testUserAuth = new UserAuth();
	}

	@After
	public void tearDown() throws Exception {
		testUserAuth = null;
	}

	@Test
	public void testGettersAndSetters() throws Exception {
		testUserAuth.set_id("1234");
		testUserAuth.setActive(true);
		testUserAuth.setDisplayName("Isaac Asimov");
		testUserAuth.setEmail("irobot@gmail.com");
		testUserAuth.setFirst_name("Isaac");
		testUserAuth.setLast_name("Asimov");
		testUserAuth.setGroups(new String[] { "writers", "robots" });
		testUserAuth.setOrganization_id("SFWA");
		testUserAuth.setShib_id("shib_1234");
		testUserAuth.setPhone_numbers(new String[] { "123", "456" });
		assertEquals(testUserAuth.get_id(), "1234");
		assertEquals(testUserAuth.getActive(), true);
		assertEquals(testUserAuth.getDisplayName(), "Isaac Asimov");
		assertEquals(testUserAuth.getEmail(), "irobot@gmail.com");
		assertEquals(testUserAuth.getFirst_name(), "Isaac");
		assertEquals(testUserAuth.getLast_name(), "Asimov");
		assertArrayEquals(testUserAuth.getGroups(), new String[] { "writers", "robots" });
		assertEquals(testUserAuth.getOrganization_id(), "SFWA");
		assertEquals(testUserAuth.getShib_id(), "shib_1234");
		assertArrayEquals(testUserAuth.getPhone_numbers(), new String[] { "123", "456" });
	}

	@Test
	public void testToString() {
		testUserAuth.set_id("1234");
		testUserAuth.setActive(true);
		testUserAuth.setDisplayName("Isaac Asimov");
		testUserAuth.setEmail("irobot@gmail.com");
		testUserAuth.setFirst_name("Isaac");
		testUserAuth.setLast_name("Asimov");
		testUserAuth.setGroups(new String[] { "writers", "robots" });
		testUserAuth.setOrganization_id("SFWA");
		testUserAuth.setShib_id("shib_1234");
		testUserAuth.setPhone_numbers(new String[] { "123", "456" });
		assertEquals(testUserAuth.toString(),
				"UserAuth{_id='1234', active=true, email='irobot@gmail.com', first_name='Isaac', groups=[writers, robots], last_name='Asimov', organization_id='SFWA', phone_numbers=[123, 456], displayName='Isaac Asimov', shib_id='shib_1234'}");
	}

	@Test
	public void testToJson() throws Exception {
		testUserAuth.set_id("1234");
		testUserAuth.setActive(true);
		testUserAuth.setDisplayName("Isaac Asimov");
		testUserAuth.setEmail("irobot@gmail.com");
		testUserAuth.setFirst_name("Isaac");
		testUserAuth.setLast_name("Asimov");
		testUserAuth.setGroups(new String[] { "writers", "robots" });
		testUserAuth.setOrganization_id("SFWA");
		testUserAuth.setShib_id("shib_1234");
		testUserAuth.setPhone_numbers(new String[] { "123", "456" });
		assertEquals(testUserAuth.toJson(),
				"{\"displayName\":\"Isaac Asimov\",\"email\":\"irobot@gmail.com\",\"_id\":\"1234\",\"active\":true,\"first_name\":\"Isaac\",\"groups\":[\"writers\",\"robots\"],\"last_name\":\"Asimov\",\"organization_id\":\"SFWA\",\"phone_numbers\":[\"123\",\"456\"],\"shib_id\":\"shib_1234\"}");
	}
}
