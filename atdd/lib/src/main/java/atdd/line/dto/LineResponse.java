package atdd.line.dto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import atdd.line.domain.Line;
import atdd.station.dto.StationResponse;

public class LineResponse {

    private Long id;
    private String color;
    private String name;
    private int extraFee;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private List<StationResponse> stations;

    public LineResponse() {}

    public LineResponse(Long id, String color, String name, LocalDateTime createdDate, LocalDateTime modifiedDate,
            List<StationResponse> stations, int extraFee) {
        this.id = id;
        this.color = color;
        this.name = name;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.stations = stations;
        this.extraFee = extraFee;
    }

    public Long getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public int getExtraFee() {
        return extraFee;
    }

    public static LineResponse from(Line line) {
        List<StationResponse> stations = line.getStations().stream()
            .map(StationResponse::from)
            .collect(Collectors.toList());
        return new LineResponse(line.getId(), line.getColor(), line.getName(), line.getCreatedDate(),
            line.getModifiedDate(), stations, line.getExtraFee());
    }

    public static LineResponse exceptStationFrom(Line line) {
        return new LineResponse(line.getId(), line.getColor(), line.getName(), line.getCreatedDate(),
            line.getModifiedDate(), Collections.emptyList(), line.getExtraFee());
    }
}
