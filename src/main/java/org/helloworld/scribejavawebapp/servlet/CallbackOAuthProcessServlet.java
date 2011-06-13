package org.helloworld.scribejavawebapp.servlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.helloworld.scribejavawebapp.model.ScribeServiceBuilderProvider;
import org.helloworld.scribejavawebapp.model.User;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CallbackOAuthProcessServlet
        extends HttpServlet {

    private Log log = LogFactory.getLog(getClass());


    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        log.info("Calling CallbackOAuthProcessServlet.doGet()");

        User user = (User) req.getSession().getAttribute("user");

        OAuthService service = ScribeServiceBuilderProvider.getOAuthService(user.getOAuthType());

        String oAuthVerifier = req.getParameter("oauth_verifier");
        log.info("oAuthVerifier: " + oAuthVerifier);
        String oAuthToken = req.getParameter("oauth_token");
        log.info("oAuthToken: " + oAuthToken);

        Verifier verifier = new Verifier(oAuthVerifier);
        Token accessToken = service.getAccessToken(user.getOAuthRequestToken(), verifier);

        OAuthRequest request = new OAuthRequest(Verb.GET, user.getOAuthType().getRequestUrl());
        service.signRequest(accessToken, request);
        request.addHeader("GData-Version", "3.0");
//        request.addHeader("GData-Version", "2.0");
        Response response = request.send();

        log.info("Response: ");
        log.info(response.getCode());
        log.info(response.getBody());

    }

}
