package org.kpmp.auth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @Mock
    private UserPortalService userPortalService;
    private AuthController authController;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        authController = new AuthController(userPortalService);
    }

    @After
    public void tearDown() throws Exception {
        authController = null;
    }

    @Test
    public void testGetUserAuth() throws Exception {
        UserAuth userAuth = new UserAuth();
        userAuth.setShib_id("shibId");
        when(userPortalService.getUserAuth("shibId")).thenReturn(userAuth);
        assertEquals(authController.getUserInfo("shibId"), userAuth);
        assertEquals(authController.getUserInfo("shibId").getShib_id(), "shibId");
    }

    @Test
    public void testGetUserAuthNotFound() {
        when(userPortalService.getUserAuth("shibId")).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        exception.expect(ResponseStatusException.class);
        exception.expectMessage("User shibId not found");
        authController.getUserInfo("shibId");
    }

    @Test
    public void testUserPortalProblem() {
        when(userPortalService.getUserAuth("shibId")).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        exception.expect(ResponseStatusException.class);
        exception.expectMessage("There was a problem connecting to the User Portal");
        authController.getUserInfo("shibId");
    }

    @Test
    public void testGetUserAuthWithClient() throws Exception {
        UserAuth userAuth = new UserAuth();
        userAuth.setShib_id("shibId");
        when(userPortalService.getUserAuthWithClient("clientId", "shibId")).thenReturn(userAuth);
        assertEquals(authController.getUserInfoWithClient("clientId", "shibId"), userAuth);
        assertEquals(authController.getUserInfoWithClient("clientId", "shibId").getShib_id(), "shibId");
    }

    @Test
    public void testGetUserAuthWithClientNotFound() {
        when(userPortalService.getUserAuthWithClient("clientId", "shibId")).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        exception.expect(ResponseStatusException.class);
        exception.expectMessage("User not found");
        authController.getUserInfoWithClient("clientId", "shibId");
    }

    @Test
    public void testGetUserAuthWithClientPortalProblem() {
        when(userPortalService.getUserAuthWithClient("clientId", "shibId")).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        exception.expect(ResponseStatusException.class);
        exception.expectMessage("There was a problem connecting to the User Portal");
        authController.getUserInfoWithClient("clientId", "shibId");
    }

}
