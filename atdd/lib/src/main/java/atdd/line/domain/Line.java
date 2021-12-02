package atdd.line.domain;

import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

import atdd.common.BaseEntity;
import atdd.section.domain.Section;
import atdd.section.domain.Sections;
import atdd.station.domain.Station;

@Entity
public class Line extends BaseEntity {

    public static final Line EMPTY = new Line();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Embedded
    private Sections sections = new Sections();

    private String color;

    @ColumnDefault("0")
    private int extraFee;

    public Line() {}

    public Line(Long id, String name, String color, Sections sections, int extraFee) {
        this.id = id;
        this.name = name;
        this.sections = sections;
        this.color = color;
        this.extraFee = extraFee;
    }

    public Line(String color, String name, int extraFee) {
        this.color = color;
        this.name = name;
        this.extraFee = extraFee;
    }

    public Line(Long id, String color, String name, Station upStation, Station downSation, int distance, int extraFee) {
        this(color, name, extraFee);
        sections.add(new Section(this, upStation, upStation, distance));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void addSection(Section section) {
        sections.add(section);
    }

    public void deleteSection(Station station) {
        sections.delete(station);
    }

    public List<Station> getStations() {
        return Collections.unmodifiableList(sections.getStations());
    }

    public List<Section> getSections() {
        return Collections.unmodifiableList(sections.getSections());
    }

    public int getExtraFee() {
        return extraFee;
    }

    public void update(Line line) {
        this.color = line.getColor();
        this.name = line.getName();
        this.extraFee = line.getExtraFee();
    }

}
