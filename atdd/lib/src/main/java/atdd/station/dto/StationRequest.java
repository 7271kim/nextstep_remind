package atdd.station.dto;

public class StationRequest {
    private String name;

    public StationRequest() {}

    public StationRequest(String name) {
        this.name = name;
    }

    public static StationRequest of(String name) {
        return new StationRequest(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
