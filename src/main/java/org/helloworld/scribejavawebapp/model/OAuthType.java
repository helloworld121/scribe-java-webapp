package org.helloworld.scribejavawebapp.model;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.GoogleApi;

/**
 * User: hal
 * Date: 12.06.2011
 * Time: 16:38:06
 */
public enum OAuthType {

    GOOGLE("google", GoogleApi.class, "anonymous", "anonymous",
            "https://docs.google.com/feeds/",
            "http://localhost:8080/callbackoauth",
            "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=",
            "https://docs.google.com/feeds/default/private/full/")
    ;

    private String name;
    private Class<? extends Api> apiClass;
    private String apiKey;
    private String apiSecret;
    private String scope;
    private String callback;
    private String authorizeUrl;
    private String requestUrl;

    private OAuthType(String name, Class<? extends Api> apiClass,
                      String apiKey, String apiSecret, String scope,
                      String callback, String authorizeUrl, String requestUrl) {
        this.name = name;
        this.apiClass = apiClass;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.scope = scope;
        this.callback = callback;
        this.authorizeUrl = authorizeUrl;
        this.requestUrl = requestUrl;
    }

    public String getName() {
        return name;
    }
    public Class<? extends Api> getApiClass() {
        return apiClass;
    }
    public String getApiKey() {
        return apiKey;
    }
    public String getApiSecret() {
        return apiSecret;
    }
    public String getScope() {
        return scope;
    }
    public String getCallback() {
        return callback;
    }
    public String getAuthorizeUrl() {
        return authorizeUrl;
    }
    public String getRequestUrl() {
        return requestUrl;
    }


    public static OAuthType getOAuthTypeByName(String name) {
        for(OAuthType type : OAuthType.values()) {
            if(type.name.equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Can't find OAuthType with name: '" + name + "'");
    }

}
