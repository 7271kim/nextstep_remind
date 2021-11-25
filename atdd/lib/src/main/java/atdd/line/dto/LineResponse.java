package atdd.line.dto;

import java.time.LocalDateTime;

import atdd.line.domain.Line;

public class LineResponse {
    private Long id;
    private String color;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public LineResponse() {}

    public LineResponse(Long id, String color, String name, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.color = color;
        this.name = name;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
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

    public static LineResponse from(Line line) {
        return new LineResponse(line.getId(), line.getColor(), line.getName(), line.getCreatedDate(), line.getModifiedDate());
    }
}
