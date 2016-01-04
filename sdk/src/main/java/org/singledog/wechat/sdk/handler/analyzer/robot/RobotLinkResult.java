package org.singledog.wechat.sdk.handler.analyzer.robot;

/**
 *
 * Created by adam on 16-1-4.
 */
public class RobotLinkResult extends RobotTextResult {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return new StringBuilder(this.getText()).append(", 详细：").append(this.getUrl()).toString();
    }
}
