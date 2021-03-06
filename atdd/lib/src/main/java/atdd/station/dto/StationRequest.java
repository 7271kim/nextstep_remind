package atdd.station.dto;

import atdd.station.domain.Station;

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

    public Station toStation() {
        return new Station(name);
    }

}
