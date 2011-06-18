package org.helloworld.scribejavawebapp.servlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scribe.webapp.oauth.boundary.OAuthService;
import org.scribe.webapp.oauth.boundary.OAuthUser;
import org.scribe.webapp.oauth.boundary.exception.OAuthProviderException;

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

        log.info("URL: " + fullRequestUrl(req));

        OAuthUser user = (OAuthUser) req.getSession().getAttribute("user");

        String oAuthVerifier = req.getParameter("oauth_verifier");
        user.setOAuthVerifier(oAuthVerifier);
        log.info("oAuthVerifier: " + oAuthVerifier);

        String oAuthToken = req.getParameter("oauth_token");
        log.info("oAuthToken: " + oAuthToken);


        // calling service
        OAuthService service = new OAuthService();
        try {
            user = service.readingUserData(user);
        } catch (OAuthProviderException e) {
            log.error(e.getMessage(), e);
            throw new ServletException(e);
        }


        // Logging
        log.info("User: providerUserId => " + user.getProviderUserId());
        log.info("User: nickname => " + user.getNickname());
        log.info("User: name => " + user.getName());
        log.info("User: eMail => " + user.getEMail());


        // put it to session
        req.getSession().setAttribute("user", user);

        res.sendRedirect("index.html");

    }

    private String fullRequestUrl(HttpServletRequest request) {
        String reqUrl = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        if (queryString != null) {
            reqUrl += "?"+queryString;
        }

        return reqUrl;
    }

}
