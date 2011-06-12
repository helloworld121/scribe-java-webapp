package org.helloworld.scribejavawebapp.model;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.oauth.OAuthService;

/**
 * User: hal
 * Date: 12.06.2011
 * Time: 16:48:58
 */
public class ScribeServiceBuilderProvider {

    public static OAuthService getOAuthService(OAuthType type) {

        if(type == OAuthType.GOOGLE) {
            OAuthService service = new ServiceBuilder()
                    .provider(type.getApiClass())
                    .apiKey(type.getApiKey())
                    .apiSecret(type.getApiSecret())
                    .scope(type.getScope())
                    .callback(type.getCallback())
                    .build();
            return service;
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type.getName());
        }
    }

}
