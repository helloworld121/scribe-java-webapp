package org.helloworld.scribejavawebapp.model;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.GoogleApi;
import org.scribe.builder.api.YahooApi;

public enum OAuthType {

    GOOGLE("google", GoogleApi.class,
            "anonymous",
            "anonymous",
            "http://www.google.com/m8/feeds/",
            "http://localhost:8080/callbackoauth",
//            "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=",
            "http://www.google.com/m8/feeds/contacts/default/full",
            "GData-Version", "3.0")
    ,
    YAHOO("yahoo", YahooApi.class,
            "dj0yJmk9TXZDWVpNVVdGaVFmJmQ9WVdrOWMweHZXbkZLTkhVbWNHbzlNVEl5TWprd05qUTJNZy0tJnM9Y29uc3VtZXJzZWNyZXQmeD0wMw--",
            "262be559f92a2be20c4c039419018f2b48cdfce9",
            null,
            "http://localhost:8080/callbackoauth",
//            null,
//            "http://social.yahooapis.com/v1/user/A6ROU63MXWDCW3Y5MGCYWVHDJI/profile/status?format=json",
            "http://social.yahooapis.com/v1/me/guid",
            null, null)
    ;
    

//    GOOGLE("google", GoogleApi.class, "anonymous", "anonymous",
//            "https://docs.google.com/feeds/",
//            "http://localhost:8080/callbackoauth",
//            "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=",
//            "https://docs.google.com/feeds/default/private/full/")
//    ;

    private String name;
    private Class<? extends Api> apiClass;
    private String apiKey;
    private String apiSecret;
    private String scope;
    private String callback;
//    private String authorizeUrl;
    private String requestUrl;
    private String headerName;
    private String headerValue;

    private OAuthType(String name, Class<? extends Api> apiClass,
                      String apiKey, String apiSecret, String scope,
                      String callback,
//                      String authorizeUrl,
                      String requestUrl,
                      String headerName, String headerValue) {
        this.name = name;
        this.apiClass = apiClass;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.scope = scope;
        this.callback = callback;
//        this.authorizeUrl = authorizeUrl;
        this.requestUrl = requestUrl;
        this.headerName = headerName;
        this.headerValue = headerValue;
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
//    public String getAuthorizeUrl() {
//        return authorizeUrl;
//    }
    public String getRequestUrl() {
        return requestUrl;
    }
    public String getHeaderName() {
        return headerName;
    }
    public String getHeaderValue() {
        return headerValue;
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
