package atdd.line.dto;

import atdd.line.domain.Line;

public class LineRequest {
    private String color;
    private String name;
    private Long upStationId;
    private Long downStationId;
    private int distance;
    private int extraFee;

    public LineRequest() {}

    public LineRequest(String color, String name, int extraFee) {
        this.color = color;
        this.name = name;
        this.extraFee = extraFee;
    }

    public LineRequest(String color, String name, Long upStationId, Long downStationId, int distance, int extraFee) {
        this.color = color;
        this.name = name;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
        this.extraFee = extraFee;
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

    public int getExtraFee() {
        return extraFee;
    }

    public Line toLine() {
        return new Line(color, name, extraFee);
    }

    public static LineRequest of(String color, String name, Long upStation, Long downStation, int distance, int extraFee) {
        return new LineRequest(color, name, upStation, downStation, distance, extraFee);
    }

    public static LineRequest of(String color, String name, int extraFee) {
        return new LineRequest(color, name, extraFee);
    }

}
