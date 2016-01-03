package org.singledog.wechat.sdk.message;

/**
 * Created by adam on 1/2/16.
 */
public class LocationEvent extends AbstractEventMessage {

    private double Latitude;

    private double Longitude;

    private double Precision;

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getPrecision() {
        return Precision;
    }

    public void setPrecision(double precision) {
        Precision = precision;
    }
}
