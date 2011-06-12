package org.helloworld.scribejavawebapp;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Scanner;

/**
 * User: hal
 * Date: 12.06.2011
 * Time: 12:57:37
 */
public class OAuthServlet
        extends HttpServlet {

    private Log log = LogFactory.getLog(getClass());

    private static String CALLBACK = "http://localhost:8080/oauth";

    private OAuthService myService;
    private Token myRequestToken;

    public void init()
            throws ServletException {

        super.init();
        log.info("Calling OAuthServlet.init()");
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        log.info("Calling OAuthServlet.doGet()");
        
        String provider = req.getParameter("provider");
        String protectedResource = "https://docs.google.com/feeds/default/private/full/";
        String scope = "https://docs.google.com/feeds/";

        if(StringUtils.isNotBlank(provider)) {
            log.info("Building redirect url for provider: " + provider);
            String authorizeUrl = "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=";

            myService = new ServiceBuilder()
                                  .provider(GoogleApi.class)
                                  .apiKey("anonymous")
                                  .apiSecret("anonymous")
                                  .scope(scope)
                                  .callback(CALLBACK)
                                  .build();
            myRequestToken = myService.getRequestToken();
            log.info("requestToken: " + myRequestToken);

            String redirect = authorizeUrl + myRequestToken.getToken();
            log.info("Redirect URL: " + redirect);

            res.sendRedirect(redirect);
        } else {

            String oAuthVerifier = req.getParameter("oauth_verifier");
            log.info("oAuthVerifier: " + oAuthVerifier);
            String oAuthToken = req.getParameter("oauth_token");
            log.info("oAuthToken: " + oAuthToken);


            String google = "https://docs.google.com/feeds/default/private/full/";


            Verifier verifier = new Verifier(oAuthVerifier);
            Token accessToken = myService.getAccessToken(myRequestToken, verifier);

            OAuthRequest request = new OAuthRequest(Verb.GET, google);
    myService.signRequest(accessToken, request);
    request.addHeader("GData-Version", "3.0");
    Response response = request.send();
    System.out.println("Got it! Lets see what we found...");
    System.out.println();
    System.out.println(response.getCode());
    System.out.println(response.getBody());



        }

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
