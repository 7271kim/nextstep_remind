package atdd.line.domain;

import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import atdd.common.BaseEntity;
import atdd.section.domain.Section;
import atdd.section.domain.Sections;

@Entity
public class Line extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Embedded
    private Sections sections = new Sections();

    private String color;

    public Line() {}

    public Line(String color, String name) {
        this.color = color;
        this.name = name;
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

    public List<Section> getAll() {
        return Collections.unmodifiableList(sections.getSections());
    }

    public void deleteSection(Long upstationId) {
        sections.delete(upstationId);
    }

}
