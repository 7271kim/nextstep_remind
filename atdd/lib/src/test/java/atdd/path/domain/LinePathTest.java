package atdd.path.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import atdd.common.InputException;
import atdd.line.domain.Line;
import atdd.member.domain.Member;
import atdd.section.domain.Section;
import atdd.section.domain.Sections;
import atdd.station.domain.Station;

public class LinePathTest {
    private Line 신분당선;
    private Line 이호선;
    private Line 삼호선;
    private Line 사호선;
    private Line 자바선;
    private Line 호남선;
    private Line 서해선;
    private Line 오호선;
    private Line 육호선;
    private Station 교대역;
    private Station 강남역;
    private Station 선릉역;
    private Station 잠실역;
    private Station 남부터미널역;
    private Station 양재역;
    private Station 오리역;
    private Station 분당역;
    private Station 시흥대야역;
    private Station 은계역;
    private Station 사당역;
    private Station 서울대역;
    private Station 잠실나루역;
    private Station 강변역;
    private Station 개성역;
    private LinePathSearch linePathSearch;

    @BeforeEach
    void setUp() {
        /**                           개성역
         *                            |
         *                            *신분당선*(60)
         *                            |
         * 교대역    --- *2호선*(10) ---   강남역  --- *2호선*(10) --- 선릉역--- *2호선*(5) --- 잠실역 --- 5호선(10)(추가요금 900) --- 잠실나루역 --- 6호선(20)(추가요금 1000) --- 강변역
         * |                          |                         |                     |
         * *3호선*(3)                  *신분당선*(10)              *자바선*(1)             *호남선*(10)
         * |                          |                         |                     |
         * 남부터미널역  --- *3호선*(2) --- 양재역 ---  *4호선*(7) --- 오리역 --- *4호선*(10) --- 분당역
         */

        /**
         * 시흥대야역
         * |
         * *서해선*(3)
         * |
         * 은계역
         */

        교대역 = new Station(1L, "교대역");
        강남역 = new Station(2L, "강남역");
        선릉역 = new Station(3L, "선릉역");
        잠실역 = new Station(4L, "잠실역");
        남부터미널역 = new Station(5L, "남부터미널역");
        양재역 = new Station(6L, "양재역");
        오리역 = new Station(7L, "오리역");
        분당역 = new Station(8L, "분당역");
        시흥대야역 = new Station(9L, "시흥대야역");
        은계역 = new Station(10L, "은계역");
        사당역 = new Station(11L, "사당역");
        서울대역 = new Station(12L, "서울대역");
        잠실나루역 = new Station(13L, "잠실나루역");
        강변역 = new Station(14L, "강변역");
        개성역 = new Station(14L, "개성역");

        신분당선 = new Line(1L, "신분당선", "bg-red-600", new Sections(), 0);
        신분당선.addSection(new Section(1l, 신분당선, 강남역, 양재역, 10));

        이호선 = new Line(2L, "신분당선", "bg-red-600", new Sections(), 0);
        이호선.addSection(new Section(2l, 이호선, 교대역, 강남역, 10));

        삼호선 = new Line(3L, "삼호선", "bg-red-600", new Sections(), 0);
        삼호선.addSection(new Section(3l, 삼호선, 교대역, 남부터미널역, 3));

        사호선 = new Line(4L, "사호선", "bg-red-600", new Sections(), 0);
        사호선.addSection(new Section(4l, 사호선, 양재역, 오리역, 7));

        자바선 = new Line(5L, "자바선", "bg-red-600", new Sections(), 0);
        자바선.addSection(new Section(5l, 자바선, 선릉역, 오리역, 1));

        호남선 = new Line(6L, "호남선", "bg-red-600", new Sections(), 0);
        호남선.addSection(new Section(6l, 호남선, 잠실역, 분당역, 10));

        서해선 = new Line(7L, "서해선", "bg-red-600", new Sections(), 0);
        서해선.addSection(new Section(7l, 서해선, 시흥대야역, 은계역, 3));

        오호선 = new Line(8L, "오호선", "bg-red-600", new Sections(), 900);
        오호선.addSection(new Section(8l, 오호선, 잠실역, 잠실나루역, 10));

        육호선 = new Line(9L, "육호선", "bg-red-600", new Sections(), 1000);
        육호선.addSection(new Section(9l, 육호선, 잠실나루역, 강변역, 10));

        이호선.addSection(new Section(이호선, 강남역, 선릉역, 10));
        이호선.addSection(new Section(이호선, 선릉역, 잠실역, 5));

        삼호선.addSection(new Section(삼호선, 남부터미널역, 양재역, 2));
        사호선.addSection(new Section(사호선, 오리역, 분당역, 10));

        신분당선.addSection(new Section(신분당선, 개성역, 강남역, 60));

        List<Section> sectinos = new ArrayList<>();
        sectinos.addAll(이호선.getSections());
        sectinos.addAll(삼호선.getSections());
        sectinos.addAll(신분당선.getSections());
        sectinos.addAll(자바선.getSections());
        sectinos.addAll(호남선.getSections());
        sectinos.addAll(사호선.getSections());
        sectinos.addAll(서해선.getSections());
        sectinos.addAll(오호선.getSections());
        sectinos.addAll(육호선.getSections());

        linePathSearch = LinePathSearch.of(sectinos);

    }

    @Test
    @DisplayName("특정 구간의 최단경로와 거리를 반환한다.")
    void searchPathTest() {
        LinePath path = linePathSearch.searchPath(양재역, 잠실역);
        assertThat(path.getMinDistance()).isEqualTo(13);
        assertThat(path.getStations()).containsExactly(양재역, 오리역, 선릉역, 잠실역);
    }

    @Test
    @DisplayName("출발역과 도착역이 같은 경우 Exception을 발생 시킨다.")
    void sameStations() {
        assertThrows(InputException.class, () -> {
            linePathSearch.searchPath(양재역, 양재역);
        });
    }

    @Test
    @DisplayName("출발역과 도착역이 연결이 되어 있지 않은 경우 Exception을 발생 시킨다.")
    void notConnected() {
        assertThrows(InputException.class, () -> {
            linePathSearch.searchPath(교대역, 은계역);
        });
    }

    @Test
    @DisplayName("존재하지 않은 출발역이나 도착역을 조회 할 경우 Exception을 발생 시킨다.")
    void noStations() {
        assertThrows(InputException.class, () -> {
            linePathSearch.searchPath(사당역, 서울대역);
        });
    }

    @Test
    @DisplayName("요금을 확인한다.")
    void checkFare() {
        LinePath 선릉_강변 = linePathSearch.searchPath(선릉역, 강변역); //25km
        LinePath 개성_강남 = linePathSearch.searchPath(개성역, 강남역); //60km
        assertThat(선릉_강변.getFare(new Member("", "", 19))).isEqualTo(2550);// 1250 + 1000 + 300
        assertThat(선릉_강변.getFare(new Member("", "", 18))).isEqualTo(1760);// ( 900 + 1000 + 300 ) *0.8
        assertThat(선릉_강변.getFare(new Member("", "", 12))).isEqualTo(1100);// ( 900 + 1000 + 300 ) *0.5

        assertThat(개성_강남.getFare(new Member("", "", 19))).isEqualTo(2150);// 1250 + 800 + 100
        assertThat(개성_강남.getFare(new Member("", "", 18))).isEqualTo(1440);// ( 900 + 800 + 100 ) *0.8
        assertThat(개성_강남.getFare(new Member("", "", 12))).isEqualTo(900);// ( 900 + 800 + 100 ) *0.5
    }

}
