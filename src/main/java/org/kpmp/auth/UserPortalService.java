package org.kpmp.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserPortalService {

	@Value("${userportal.url}")
	private String userPortalURL;

	private final Environment env;

	public UserPortalService(Environment env) {
		this.env = env;
	}

	public UserAuth getUserAuth(String shib_id) throws HttpClientErrorException {
		return getUserAuth(env.getProperty("DEFAULT_CLIENT_ID"), shib_id);
	}

	public UserAuth getUserAuthWithClient(String clientId, String shib_id) throws HttpClientErrorException {
		return getUserAuth(clientId, shib_id);
	}

	private UserAuth getUserAuth(String clientId, String shib_id) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-API-TOKEN", clientId);
		HttpEntity<?> entity = new HttpEntity<Object>("body", headers);
		RestTemplate restTemplate = new RestTemplate();
		UserAuth userAuth = restTemplate
				.exchange(userPortalURL + "/api/user/" + shib_id, HttpMethod.GET, entity, UserAuth.class).getBody();
		return userAuth;
	}

}
