package org.kpmp.auth;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class AuthController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private static final String userFoundFmt = "Using default client. User with shib id {} found: {}";
	private static final String userNotFoundFmt = "Using default client. User with shib id {} not found";
	private static final String userClientFoundFmt = "Using client ID {}. User with shib id {} found: {}";
	private static final String userClientNotFoundFmt = "Using client ID {}. User with shib id {} not found";
	private static final String portalError = "There was a problem connecting to the User Portal. ";

	private UserPortalService userPortalService;

	@Autowired
	public AuthController(UserPortalService userPortalService) {
		this.userPortalService = userPortalService;
	}

	@RequestMapping(value = "/v1/user/info/{shibId}", method = RequestMethod.GET)
	public @ResponseBody UserAuth getUserInfo(@PathVariable("shibId") String shibId) {
		try {
			UserAuth userAuth = userPortalService.getUserAuth(shibId);
			log.info(userFoundFmt, shibId, userAuth.toString());
			return userAuth;
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().equals(NOT_FOUND)) {
				log.error(userNotFoundFmt, shibId);
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + shibId + " not found");
			} else {
				log.error(portalError + e.getStatusCode());
				throw new ResponseStatusException(e.getStatusCode(), portalError);
			}
		}
	}

	@RequestMapping(value = "/v1/user/info/{clientId}/{shibId}", method = RequestMethod.GET)
	public @ResponseBody UserAuth getUserInfoWithClient(@PathVariable("clientId") String clientId,
			@PathVariable("shibId") String shibId) {
		try {
			UserAuth userAuth = userPortalService.getUserAuthWithClient(clientId, shibId);
			log.info(userClientFoundFmt, clientId, shibId, userAuth.toString());
			return userAuth;
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().equals(NOT_FOUND)) {
				log.error(userClientNotFoundFmt, clientId, shibId);
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
			} else {
				log.error(portalError + e.getStatusCode());
				throw new ResponseStatusException(e.getStatusCode(), portalError);
			}
		}
	}

}
