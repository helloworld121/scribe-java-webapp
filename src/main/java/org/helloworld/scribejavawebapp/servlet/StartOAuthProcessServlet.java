package org.helloworld.scribejavawebapp.servlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.helloworld.scribejavawebapp.model.OAuthType;
import org.helloworld.scribejavawebapp.model.ScribeServiceBuilderProvider;
import org.helloworld.scribejavawebapp.model.User;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

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

        String provider = req.getParameter("provider");

        OAuthType type = OAuthType.getOAuthTypeByName(provider);

        OAuthService service = ScribeServiceBuilderProvider.getOAuthService(type);

        Token token = service.getRequestToken();
        log.info("requestToken: " + token);

        log.info("put RequestToken to session");
        Object attribute = req.getSession().getAttribute("user");
        User user;
        if(attribute == null) {
            user = new User();
        } else {
            user = (User) attribute;
        }
        user.setOAuthType(type);
        user.setOAuthRequestToken(token);
        req.getSession().setAttribute("user", user);


//        String redirect = type.getAuthorizeUrl() + token.getToken();
        String redirect = service.getAuthorizationUrl(token);
        log.info("Redirect URL: " + redirect);

        res.sendRedirect(redirect);
    }

}
