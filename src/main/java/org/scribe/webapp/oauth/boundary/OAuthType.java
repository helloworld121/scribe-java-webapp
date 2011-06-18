package org.scribe.webapp.oauth.boundary;

/**
 * User: hal
 * Date: 13.06.2011
 * Time: 18:18:24
 */
public enum OAuthType {

    GOOGLE("google"),
    YAHOO("yahoo"),
    TWITTER("twitter"),
    ;

    private String name;


    private OAuthType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
