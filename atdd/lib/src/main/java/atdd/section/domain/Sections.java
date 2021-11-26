package atdd.section.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import atdd.station.exception.InputException;

public class Sections {

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Section> list = new ArrayList<>();

    public Sections() {}

    public void add(Section section) {
        if (!list.contains(section)) {
            list.add(section);
        }
    }

    public List<Section> getSections() {
        return Collections.unmodifiableList(list);
    }

    public void delete(Long upstationId) {
        Section upStation = list.stream()
            .filter(section -> section.getId() == upstationId)
            .findFirst()
            .orElseThrow(InputException::new);
        list.remove(upStation);
    }

}
