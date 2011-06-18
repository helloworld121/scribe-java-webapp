package org.scribe.webapp.oauth.boundary.exception;

/**
 * User: hal
 * Date: 18.06.2011
 * Time: 11:20:37
 */
public class OAuthProviderException 
            extends Exception {

    public OAuthProviderException(){
    }

    public OAuthProviderException(String message) {
        super(message);
    }

}