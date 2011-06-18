package org.helloworld.scribejavawebapp.servlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scribe.webapp.oauth.boundary.OAuthService;
import org.scribe.webapp.oauth.boundary.OAuthType;
import org.scribe.webapp.oauth.boundary.OAuthUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class StartOAuthProcessServlet
        extends HttpServlet {

    private Log log = LogFactory.getLog(getClass());


    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        log.info("Calling StartOAuthProcessServlet.doGet()");

        // reading parameter
        String provider = req.getParameter("provider");
        OAuthType oAuthType = OAuthType.getOAuthTypeByName(provider);
        log.info("Provider: '" + provider + "' and OAuthType: '" + oAuthType.getName() + "'");

        // instanciate user
        OAuthUser user = new OAuthUser();
        user.setOAuthType(oAuthType);

        // using service
        OAuthService service = new OAuthService();
        try {
            user = service.addAuthorizationUrl(user);
        } catch(Exception e) {
            log.error(e.getMessage(), e);
            throw new ServletException(e);
        }

        // put user into session
        req.getSession().setAttribute("user", user);

        // do redirect
        log.info("Redirect URL: " + user.getOAuthAuthorizationUrl());
        res.sendRedirect(user.getOAuthAuthorizationUrl());
    }

}
