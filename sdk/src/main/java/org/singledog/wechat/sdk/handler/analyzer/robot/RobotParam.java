package org.singledog.wechat.sdk.handler.analyzer.robot;

/**
 * Created by adam on 16-1-4.
 */
public class RobotParam {

    /**
     * required
     */
    private String key;
    /**
     * required
     */
    private String info;
    /**
     * if use context, required
     */
    private String userid;

    /**
     * not required
     */
    private String loc;
    /**
     * not required
     */
    private String lon;
    /**
     * not required
     */
    private String lat;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
