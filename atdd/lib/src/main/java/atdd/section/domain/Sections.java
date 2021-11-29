package atdd.section.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import atdd.common.InputException;
import atdd.section.exception.AlreadyExistUpDownStationException;
import atdd.station.domain.Station;

public class Sections {

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Section> list = new ArrayList<>();

    public Sections() {}

    public Sections(List<Section> list) {
        this.list = list;
    }

    public void addFirst(Section section) {
        list.add(section);
    }

    public void add(Section section) {
        checkAlreadyExist(section);
        updateSection(section);
    }

    private void updateSection(Section section) {
        Section updateSection = list.stream()
            .filter(section::isMatchedStation)
            .findFirst().orElseThrow(InputException::new);

        if (section.isSameUpstation(updateSection)) {
            updateSection.updateUpstation(section.getDownStatoin());
            updateSection.updateDistance(updateSection.getDistance() - section.getDistance());
        }

        if (section.isSameDownstation(updateSection)) {
            updateSection.updateDownstation(section.getUpstation());
            updateSection.updateDistance(updateSection.getDistance() - section.getDistance());
        }

        list.add(section);
    }

    private void checkAlreadyExist(Section section) {
        list.stream().filter(section::isSameSection).findAny().ifPresent(loop -> {
            throw new AlreadyExistUpDownStationException();
        });
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

    public List<Station> getStations() {
        List<Station> result = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return result;
        }
        Section nextSection = findStartSection();
        Section endSection = null;
        while (nextSection != null) {
            result.add(nextSection.getUpstation());
            endSection = nextSection;
            nextSection = findNextSection(nextSection);
        }
        if (endSection != null) {
            result.add(endSection.getDownStatoin());
        }

        return result;
    }

    private Section findNextSection(Section before) {
        return list.stream()
            .filter(before::isBefore)
            .findAny()
            .orElse(null);
    }

    private Section findStartSection() {
        return list.stream()
            .filter(this::hasNoBefore)
            .findFirst()
            .orElseThrow(InputException::new);
    }

    private Section findEndSection() {
        return list.stream()
            .filter(this::hasNoNext)
            .findFirst()
            .orElseThrow(InputException::new);
    }

    private boolean hasNoBefore(Section compare) {
        return list.stream().noneMatch(section -> section.isBefore(compare));
    }

    private boolean hasNoNext(Section compare) {
        return list.stream().noneMatch(compare::isBefore);
    }

}
