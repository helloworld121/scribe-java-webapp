package org.scribe.webapp.oauth.control;

import org.scribe.webapp.oauth.boundary.OAuthUser;
import org.scribe.webapp.oauth.boundary.exception.OAuthProviderException;

/**
 * User: hal
 * Date: 13.06.2011
 * Time: 20:23:35
 */
public interface Provider {

    public void buildRedirectUrl(OAuthUser user);

    public void readUserData(OAuthUser user)
            throws OAuthProviderException;

}
