package atdd.path;

import static atdd.line.LineAcceptantTest.*;
import static atdd.section.SectionAcceptantTest.*;
import static atdd.station.StationAcceptantTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import atdd.AcceptanceTest;
import atdd.line.dto.LineResponse;
import atdd.station.dto.StationResponse;

@DisplayName("경로 찾기 인수테스트")
public class PathAcceptantTest extends AcceptanceTest {

    private LineResponse 신분당선;
    private LineResponse 이호선;
    private LineResponse 삼호선;
    private LineResponse 오호선;
    private LineResponse 육호선;

    private StationResponse 강남역;
    private StationResponse 양재역;
    private StationResponse 교대역;
    private StationResponse 남부터미널역;
    private StationResponse 선릉역;
    private StationResponse 잠실역;
    private StationResponse 오리역;
    private StationResponse 분당역;
    private LineResponse 사호선;
    private LineResponse 자바선;
    private LineResponse 호남선;
    private StationResponse 시흥대야역;
    private StationResponse 은계역;
    private LineResponse 서해선;
    private StationResponse 사당역;
    private StationResponse 서울대역;
    private StationResponse 개성역;
    private StationResponse 잠실나루역;
    private StationResponse 강변역;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
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

        //given
        //given
        교대역 = 지하철역_생성요청("교대역");
        강남역 = 지하철역_생성요청("강남역");
        선릉역 = 지하철역_생성요청("선릉역");
        잠실역 = 지하철역_생성요청("잠실역");
        남부터미널역 = 지하철역_생성요청("남부터미널역");
        양재역 = 지하철역_생성요청("양재역");
        오리역 = 지하철역_생성요청("오리역");
        분당역 = 지하철역_생성요청("분당역");
        시흥대야역 = 지하철역_생성요청("시흥대야역");
        은계역 = 지하철역_생성요청("은계역");
        사당역 = 지하철역_생성요청("사당역");
        서울대역 = 지하철역_생성요청("서울대역");
        개성역 = 지하철역_생성요청("개성역");
        잠실나루역 = 지하철역_생성요청("잠실나루역");
        강변역 = 지하철역_생성요청("강변역");

        신분당선 = 지하철_노선_생성요청("신분당선", "bg-red-600", 강남역.getId(), 양재역.getId(), 10, 0);
        이호선 = 지하철_노선_생성요청("이호선", "bg-red-600", 교대역.getId(), 강남역.getId(), 10, 0);
        삼호선 = 지하철_노선_생성요청("삼호선", "bg-red-600", 교대역.getId(), 양재역.getId(), 5, 0);
        사호선 = 지하철_노선_생성요청("사호선", "bg-red-600", 양재역.getId(), 오리역.getId(), 7, 0);
        오호선 = 지하철_노선_생성요청("오호선", "bg-red-600", 잠실역.getId(), 잠실나루역.getId(), 10, 900);
        육호선 = 지하철_노선_생성요청("육호선", "bg-red-600", 잠실나루역.getId(), 강변역.getId(), 10, 1000);
        자바선 = 지하철_노선_생성요청("자바선", "bg-red-600", 선릉역.getId(), 오리역.getId(), 1, 0);
        호남선 = 지하철_노선_생성요청("호남선", "bg-red-600", 잠실역.getId(), 분당역.getId(), 10, 0);
        서해선 = 지하철_노선_생성요청("서해선", "bg-red-600", 시흥대야역.getId(), 은계역.getId(), 3, 0);

        지하철_구간_생성요청(강남역.getId(), 선릉역.getId(), 10, 이호선.getId());
        지하철_구간_생성요청(선릉역.getId(), 잠실역.getId(), 5, 이호선.getId());
        지하철_구간_생성요청(교대역.getId(), 남부터미널역.getId(), 3, 삼호선.getId());
        지하철_구간_생성요청(오리역.getId(), 분당역.getId(), 10, 사호선.getId());
        지하철_구간_생성요청(개성역.getId(), 강남역.getId(), 60, 신분당선.getId());
    }

    @Test
    @DisplayName("정상 경로 찾기 테스트")
    void createTest() {

    }

    @Test
    @DisplayName("연결되지 않는 역으로 경로찾을 시 오류를 출력한다.")
    void noConnectTest() {}

    @Test
    @DisplayName("나이대별 요금이 다른지 테스트")
    void ageFareTest() {}

}
