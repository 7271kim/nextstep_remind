package atdd.line.dto;

import atdd.line.domain.Line;

public class LineRequest {
    private String color;
    private String name;
    private Long upStationId;
    private Long downStationId;
    private int distance;

    public LineRequest() {}

    public LineRequest(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public LineRequest(String color, String name, Long upStationId, Long downStationId, int distance) {
        this.color = color;
        this.name = name;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public Long getUpStationId() {
        return upStationId;
    }

    public Long getDownStationId() {
        return downStationId;
    }

    public int getDistance() {
        return distance;
    }

    public static LineRequest of(String color, String name, Long upStation, Long downStation, int distance) {
        return new LineRequest(color, name, upStation, downStation, distance);
    }

    public Line toLine() {
        return new Line(color, name);
    }

    public static LineRequest of(String color, String name) {
        return new LineRequest(color, name);
    }

}
