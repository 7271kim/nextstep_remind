package atdd.line.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import atdd.line.domain.Line;
import atdd.station.dto.StationResponse;

public class LineResponse {

    private Long id;
    private String color;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private List<StationResponse> stations;

    public LineResponse() {}

    public LineResponse(Long id, String color, String name, LocalDateTime createdDate, LocalDateTime modifiedDate,
            List<StationResponse> stations) {
        this.id = id;
        this.color = color;
        this.name = name;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.stations = stations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
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

    public void setStations(List<StationResponse> stations) {
        this.stations = stations;
    }

    public static LineResponse from(Line line) {
        List<StationResponse> stations = line.getStations().stream()
            .map(StationResponse::from)
            .collect(Collectors.toList());
        return new LineResponse(line.getId(), line.getColor(), line.getName(), line.getCreatedDate(),
            line.getModifiedDate(), stations);
    }
}
