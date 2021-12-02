package atdd.path.dto;

import java.util.List;
import java.util.stream.Collectors;

import atdd.station.domain.Station;
import atdd.station.dto.StationResponse;

public class PathResponse {
    private List<StationResponse> stations;
    private int distance;
    private int fare;

    public PathResponse() {}

    private PathResponse(List<StationResponse> stations, int distance, int fare) {
        this.stations = stations;
        this.distance = distance;
        this.fare = fare;
    }

    public static PathResponse of(List<Station> stations, int distance, int fare) {
        return new PathResponse(toReseponse(stations), distance, fare);
    }

    private static List<StationResponse> toReseponse(List<Station> stations) {
        return stations.stream()
            .map(StationResponse::from)
            .collect(Collectors.toList());
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }

    public int getFare() {
        return fare;
    }

}
