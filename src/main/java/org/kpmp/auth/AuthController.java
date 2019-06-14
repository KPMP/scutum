package org.kpmp.auth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.auth0.jwt.interfaces.DecodedJWT;

import users.User;

@Controller
public class AuthController {

    private HttpSession session;
    private ShibbolethUserService userService;
    private UTF8Encoder encoder;
    private TokenService tokenService;

    @Autowired
    public AuthController(ShibbolethUserService userService, UTF8Encoder encoder, TokenService tokenService) {
        this.userService = userService;
        this.encoder = encoder;
        this.tokenService = tokenService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public @ResponseBody RedirectView login(HttpServletRequest request, HttpSession httpSession) throws UnsupportedEncodingException {
        String redirectURL = request.getParameter("redirect");
        session = httpSession;
        User user = userService.getUser(request, encoder);
        System.out.println("user from shib headers " + session.getId() + " " + user.getDisplayName());
        session.setAttribute("user", user);
        return new RedirectView(redirectURL);
    }

    @CrossOrigin
    @RequestMapping(value = "/auth")
    public @ResponseBody AuthResponse getAuth(@RequestBody (required = false) Map<String, Object> payload) throws IOException {
        AuthResponse auth = new AuthResponse();
        String tokenString = (String) payload.get("token");
        if (tokenString != null) {
            DecodedJWT verifiedToken = tokenService.verifyToken(tokenString);
            if (verifiedToken != null) {
                auth.setToken(verifiedToken.getToken());
                System.out.println("user from token " + session.getId() + " " + tokenService.getUserFromToken(verifiedToken).getDisplayName());
                auth.setUser(tokenService.getUserFromToken(verifiedToken));
            }
        }

        if (session != null && auth.getToken() == null) {
            User user = (User) session.getAttribute("user");
            System.out.println("user from session " + session.getId() + " " + user.getDisplayName());
            tokenString = tokenService.buildTokenWithUser(user);
            auth.setToken(tokenString);
            auth.setUser(user);
            session = null;
        }
        if (auth != null) {
            System.out.println("user from auth obj " + session.getId() + " " + auth.getUser().getDisplayName());
        }
        return auth;
    }

}
