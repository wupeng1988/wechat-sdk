package org.singledog.wechat.sdk.token;

public class AccessToken {

    private String access_token;
    private int expires_in;//秒

    public String getAccess_token() {
        return access_token;
    }

    void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}