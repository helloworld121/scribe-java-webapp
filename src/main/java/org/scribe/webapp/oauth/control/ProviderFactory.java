package org.scribe.webapp.oauth.control;

import org.scribe.webapp.oauth.boundary.OAuthType;

/**
 * User: hal
 * Date: 13.06.2011
 * Time: 20:23:06
 */
public class ProviderFactory {

    public static Provider getProvider(OAuthType oAuthType) {
        if(oAuthType == null) {
            throw new IllegalArgumentException("OAuth must not be null!");
        }

        if(oAuthType == OAuthType.GOOGLE) {
            return new ProviderGoogle();
        } else if(oAuthType == OAuthType.YAHOO) {
            return new ProviderYahoo();
        } else {
            throw new IllegalArgumentException("Unsupported OAuth provider: " + oAuthType.getName());
        }
    }

}
