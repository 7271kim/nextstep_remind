package atdd.section.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import org.springframework.util.CollectionUtils;

import atdd.common.InputException;
import atdd.section.exception.AlreadyExistUpDownStationException;
import atdd.section.exception.MinimumException;
import atdd.station.domain.Station;

public class Sections {

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Section> list = new ArrayList<>();

    public Sections() {}

    public Sections(List<Section> list) {
        this.list = list;
    }

    public void add(Section section) {
        if (list.isEmpty()) {
            list.add(section);
            return;
        }
        validateAlreadyOrNoExist(section);
        updateSection(section);
    }

    private void updateSection(Section section) {
        ifMatchedUpstation(section);
        ifMatchedDonwnStation(section);
        list.add(section);
    }

    private void ifMatchedUpstation(Section section) {
        list.stream()
            .filter(section::isSameUpstation)
            .findFirst().ifPresent(matchedSection -> {
                matchedSection.updateUpstation(section.getDownStatoin());
                matchedSection.updateDistance(matchedSection.getDistance() - section.getDistance());
            });
    }

    private void ifMatchedDonwnStation(Section section) {
        list.stream()
            .filter(section::isSameDownstation)
            .findFirst().ifPresent(matchedSection -> {
                matchedSection.updateDownstation(section.getUpstation());
                matchedSection.updateDistance(matchedSection.getDistance() - section.getDistance());
            });
    }

    private void validateAlreadyOrNoExist(Section section) {
        boolean hasUpstaion = list.stream().anyMatch(section::isMatchedUpStation);
        boolean hasDownstaion = list.stream().anyMatch(section::isMatchedDownStation);
        if (hasUpstaion && hasDownstaion) {
            throw new AlreadyExistUpDownStationException();
        }
        if (!hasUpstaion && !hasDownstaion) {
            throw new InputException();
        }
    }

    public List<Section> getSections() {
        return Collections.unmodifiableList(list);
    }

    public void delete(Station station) {
        validate(station);
        removeMatchedUpstation(station);
        removeIfEndSection(station);
    }

    private void removeIfEndSection(Station station) {
        list.stream()
            .filter(section -> section.isSameDownstation(station))
            .findFirst()
            .ifPresent(list::remove);
    }

    private void removeMatchedUpstation(Station station) {
        list.stream()
            .filter(section -> section.isSameUpstation(station))
            .findFirst()
            .ifPresent(this::removeSection);
    }

    private void validate(Station station) {

        if (CollectionUtils.isEmpty(list) || list.size() == 1) {
            throw new MinimumException();
        }

        List<Station> stationList = getStationList();

        if (!stationList.contains(station)) {
            throw new InputException();
        }
    }

    private List<Station> getStationList() {
        return list.stream()
            .map(section -> Stream.of(section.getUpstation(), section.getDownStatoin()))
            .flatMap(Stream::distinct)
            .collect(Collectors.toList());
    }

    private void removeSection(Section section) {
        Section before = findBeforeSection(section);
        if (!Section.isEmpty(before)) {
            before.updateDistance(section.getDistance() + before.getDistance());
            before.updateDownstation(section.getDownStatoin());
        }
        list.remove(section);
    }

    public List<Station> getStations() {
        List<Station> result = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return result;
        }
        Section nextSection = findStartSection();
        Section endSection = null;
        while (!Section.isEmpty(nextSection)) {
            result.add(nextSection.getUpstation());
            endSection = nextSection;
            nextSection = findNextSection(nextSection);
        }
        if (endSection != null) {
            result.add(endSection.getDownStatoin());
        }

        return result;
    }

    private Section findNextSection(Section findSection) {
        return list.stream()
            .filter(findSection::isBefore)
            .findAny()
            .orElse(Section.EMPTY);
    }

    private Section findBeforeSection(Section findSection) {
        return list.stream()
            .filter(section -> section.isBefore(findSection))
            .findAny()
            .orElse(Section.EMPTY);
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

    private boolean hasNoBefore(Section findSection) {
        return list.stream().noneMatch(section -> section.isBefore(findSection));
    }

    private boolean hasNoNext(Section findSection) {
        return list.stream().noneMatch(findSection::isBefore);
    }

    private boolean isInnerSection(Section compare) {
        return !hasNoBefore(compare) && !hasNoNext(compare);
    }

}
