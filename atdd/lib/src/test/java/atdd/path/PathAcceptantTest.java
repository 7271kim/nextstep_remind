package atdd.path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import atdd.AcceptanceTest;
import atdd.line.dto.LineResponse;
import atdd.station.StationAcceptantTest;
import atdd.station.dto.StationResponse;

@DisplayName("경로 찾기 인수테스트")
public class PathAcceptantTest extends AcceptanceTest {

    private StationResponse 강남역;
    private StationResponse 교대역;
    private StationResponse 역삼역;
    private LineResponse 이호선;

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

        강남역 = StationAcceptantTest.지하철역_생성요청("강남역");
        교대역 = StationAcceptantTest.지하철역_생성요청("교대역");
        역삼역 = StationAcceptantTest.지하철역_생성요청("역삼역");
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
