package org.scribe.webapp.oauth.control;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scribe.webapp.oauth.boundary.exception.OAuthProviderException;

import java.net.HttpURLConnection;

/**
 * User: hal
 * Date: 18.06.2011
 * Time: 11:18:01
 */
public abstract class AbstractProvider
        implements Provider {

    private Log log = LogFactory.getLog(getClass());

    protected void checkResponseCode(int code)
            throws OAuthProviderException {
        log.info("Given HTTP response code: " + code);

        if(code == HttpURLConnection.HTTP_OK) {
            // everything is ok
            return;
        }

        throw new OAuthProviderException("HTTP code: '" + code + "' indicates, that problems in communication occured.");

    }

}
