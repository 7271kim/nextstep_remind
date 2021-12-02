package atdd.section.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import atdd.line.domain.Line;
import atdd.section.exception.AlreadyExistUpDownStationException;
import atdd.section.exception.DistanceLongException;
import atdd.section.exception.MinimumException;
import atdd.station.domain.Station;

public class SectionsTest {

    private Line 이호선;
    private Station 삼성역;
    private Station 강남역;
    private Station 교대역;
    private Station 역삼역;
    private Section 강남_교대_구간;
    private Section 교대_역삼_구간;
    private Section 삼성_강남_구간;
    private Sections sections;

    @BeforeEach
    void setUp() {
        // 삼성 - 강남 - 교대 - 역삼
        이호선 = new Line("초록", "이호선", 1000);
        강남역 = new Station(1l, "강남역");
        교대역 = new Station(2l, "교대역");
        역삼역 = new Station(3l, "역삼역");
        삼성역 = new Station(4l, "삼성역");
        삼성_강남_구간 = new Section(1l, 이호선, 삼성역, 강남역, 15);
        강남_교대_구간 = new Section(2l, 이호선, 강남역, 교대역, 10);
        교대_역삼_구간 = new Section(3l, 이호선, 교대역, 역삼역, 20);

        sections = new Sections();
        sections.add(강남_교대_구간);
        sections.add(교대_역삼_구간);
        sections.add(삼성_강남_구간);
    }

    @Test
    @DisplayName("모든 역 잘 찾아오는지 확인")
    void getStationsTest() {
        assertThat(sections.getStations().stream()
            .map(Station::getName)
            .collect(Collectors.toList())).containsExactly("삼성역", "강남역", "교대역", "역삼역");
    }

    @Test
    @DisplayName("기존에 존재하는 역은 추가할 수 없다.")
    void createAlreadyTest() {
        assertThrows(AlreadyExistUpDownStationException.class, () -> {
            sections.add(new Section(5l, 이호선, 삼성역, 역삼역, 1));
        });
    }

    @Test
    @DisplayName("중간 역 잘 삭제 되는지 확인")
    void deleteInnerTest() {
        sections.delete(강남역);
        assertThat(sections.getStations().stream()
            .map(Station::getName)
            .collect(Collectors.toList())).containsExactly("삼성역", "교대역", "역삼역");
    }

    @Test
    @DisplayName("중간 역 잘 삭제 되는지 확인2")
    void deleteInnerTwoTest() {
        sections.delete(교대역);
        assertThat(sections.getStations().stream()
            .map(Station::getName)
            .collect(Collectors.toList())).containsExactly("삼성역", "강남역", "역삼역");
    }

    @Test
    @DisplayName("끝 역 잘 삭제 되는지 확인")
    void deleteEndTest() {
        sections.delete(역삼역);
        assertThat(sections.getStations().stream()
            .map(Station::getName)
            .collect(Collectors.toList())).containsExactly("삼성역", "강남역", "교대역");
    }

    @Test
    @DisplayName("끝 역 삭제 후 재삽입 되는지 확인")
    void deleteEndAddTest() {
        sections.delete(역삼역);
        sections.add(new Section(10l, 이호선, 강남역, 역삼역, 1));
        assertThat(sections.getStations().stream()
            .map(Station::getName)
            .collect(Collectors.toList())).containsExactly("삼성역", "강남역", "역삼역", "교대역");
    }

    @Test
    @DisplayName("통합 확인")
    void deleteEndAddBigDistanceTest() {
        //사당 - 80 - 방배 - 10 - 서초 - 21 - 교대 - 1111 - 강남
        Line 삼호선 = new Line("레드", "삼호선", 2000);
        sections = new Sections();
        Station 사당역 = new Station(11l, "사당역");
        Station 방배역 = new Station(12l, "방배역");
        Station 서초역 = new Station(13l, "서초역");
        sections.add(new Section(10l, 삼호선, 사당역, 교대역, 111));
        sections.add(new Section(11l, 삼호선, 사당역, 방배역, 80));
        sections.add(new Section(12l, 삼호선, 방배역, 서초역, 10));
        sections.add(new Section(13l, 삼호선, 교대역, 강남역, 1111));

        sections.delete(강남역);
        sections.add(new Section(14l, 삼호선, 서초역, 강남역, 20));
        sections.delete(교대역);

        assertThrows(DistanceLongException.class, () -> {
            sections.add(new Section(15l, 삼호선, 서초역, 교대역, 22222));
        });
    }

    @Test
    @DisplayName("첫 역 잘 삭제 되는지 확인")
    void deleteStartTest() {
        sections.delete(삼성역);
        assertThat(sections.getStations().stream()
            .map(Station::getName)
            .collect(Collectors.toList())).containsExactly("강남역", "교대역", "역삼역");
    }

    @Test
    @DisplayName("삭제는 가능하지만 최소 한개의 구간은 존재해야 한다.")
    void deleteAllTest() {
        sections.delete(삼성역);
        sections.delete(강남역);
        assertThrows(MinimumException.class, () -> {
            sections.delete(교대역);
        });
    }

}
