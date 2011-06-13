package org.scribe.webapp.oauth.boundary;

import org.scribe.model.Token;

/**
 * User: hal
 * Date: 13.06.2011
 * Time: 18:16:00
 */
public class OAuthUser {

    // type
    private OAuthType oAuthType;

    // communication parameters
    private String oAuthToken;
    private String oAuthSecret;
    private String oAuthRawResponse;
    private String oAuthVerifier;

    // result
    private String oAuthAuthorizationUrl;


    public OAuthType getOAuthType() {
        return oAuthType;
    }

    public void setOAuthType(OAuthType oAuthType) {
        this.oAuthType = oAuthType;
    }

//    public Token getoAuthToken() {
//        return oAuthToken;
//    }
//
//    public void setoAuthToken(Token oAuthToken) {
//        this.oAuthToken = oAuthToken;
//    }


    public String getOAuthToken() {
        return oAuthToken;
    }

    public void setOAuthToken(String oAuthToken) {
        this.oAuthToken = oAuthToken;
    }

    public String getOAuthSecret() {
        return oAuthSecret;
    }

    public void setOAuthSecret(String oAuthSecret) {
        this.oAuthSecret = oAuthSecret;
    }

    public String getOAuthRawResponse() {
        return oAuthRawResponse;
    }

    public void setOAuthRawResponse(String oAuthRawResponse) {
        this.oAuthRawResponse = oAuthRawResponse;
    }

    public String getOAuthVerifier() {
        return oAuthVerifier;
    }

    public void setOAuthVerifier(String oAuthVerifier) {
        this.oAuthVerifier = oAuthVerifier;
    }

    public String getOAuthAuthorizationUrl() {
        return oAuthAuthorizationUrl;
    }

    public void setOAuthAuthorizationUrl(String oAuthAuthorizationUrl) {
        this.oAuthAuthorizationUrl = oAuthAuthorizationUrl;
    }
    
}
