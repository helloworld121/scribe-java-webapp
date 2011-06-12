package org.helloworld.scribejavawebapp.model;

import org.scribe.model.Token;


public class User {

    private OAuthType oAuthType;
    private Token oAuthRequestToken;

    

    public OAuthType getOAuthType() {
        return oAuthType;
    }

    public void setOAuthType(OAuthType oAuthType) {
        this.oAuthType = oAuthType;
    }

    public Token getOAuthRequestToken() {
        return oAuthRequestToken;
    }

    public void setOAuthRequestToken(Token oAuthRequestToken) {
        this.oAuthRequestToken = oAuthRequestToken;
    }
}
