package GeoIP;

public class GeoIP {

    private String ipAddress;
    private String latitude;
    private String longitude;

    public GeoIP(String ipAddress, String latitude, String longitude) {
        this.ipAddress = ipAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
