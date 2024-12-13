package org.kpmp.auth;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthControllerTest {

    @Mock
    private UserPortalService userPortalService;
    private AuthController authController;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        authController = new AuthController(userPortalService);
    }

    @AfterEach
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
        String shibId = "shibId";
        when(userPortalService.getUserAuth(shibId)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authController.getUserInfo(shibId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User " + shibId + " not found", exception.getReason());

        verify(userPortalService).getUserAuth(shibId);
    }

    @Test
    public void testUserPortalProblem() {
        when(userPortalService.getUserAuth("shibId")).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authController.getUserInfo("shibId");
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("There was a problem connecting to the User Portal.", exception.getReason());
        verify(userPortalService).getUserAuth("shibId");
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

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authController.getUserInfoWithClient("clientId", "shibId");
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("User not found", exception.getReason());
        verify(userPortalService).getUserAuthWithClient("clientId", "shibId");
    }

    @Test
    public void testGetUserAuthWithClientPortalProblem() {
        when(userPortalService.getUserAuthWithClient("clientId", "shibId")).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authController.getUserInfoWithClient("clientId", "shibId");
        });
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("There was a problem connecting to the User Portal.", exception.getReason());
        verify(userPortalService).getUserAuthWithClient("clientId", "shibId");
    }

}
