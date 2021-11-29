package atdd.section.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import atdd.line.domain.Line;
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
        이호선 = new Line("초록", "이호선");
        강남역 = new Station(1l, "강남역");
        교대역 = new Station(2l, "교대역");
        역삼역 = new Station(3l, "역삼역");
        삼성역 = new Station(4l, "삼성역");
        삼성_강남_구간 = new Section(1l, 이호선, 삼성역, 강남역, 15);
        강남_교대_구간 = new Section(2l, 이호선, 강남역, 교대역, 10);
        교대_역삼_구간 = new Section(3l, 이호선, 교대역, 역삼역, 20);

        sections = new Sections();
        sections.addFirst(강남_교대_구간);
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
    @DisplayName("중간 역 잘 삭제 되는지 확인")
    void deleteTest() {
        sections.delete(강남역.getId());
        assertThat(sections.getStations().stream()
            .map(Station::getName)
            .collect(Collectors.toList())).containsExactly("삼성역", "교대역", "역삼역");
    }

}
