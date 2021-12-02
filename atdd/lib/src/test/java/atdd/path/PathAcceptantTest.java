package atdd.path;

import static atdd.auth.AuthAcceptanceTest.*;
import static atdd.line.LineAcceptantTest.*;
import static atdd.member.MemberAcceptanceTest.*;
import static atdd.section.SectionAcceptantTest.*;
import static atdd.station.StationAcceptantTest.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import atdd.AcceptanceTest;
import atdd.auth.dto.TokenResponse;
import atdd.line.dto.LineResponse;
import atdd.path.dto.PathResponse;
import atdd.station.dto.StationResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

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
    void findPathBySourceAndTargetSuccess() {
        //when
        PathResponse 양재_잠실 = 최단거리_경로_요청(양재역.getId(), 잠실역.getId());
        PathResponse 강남_분당 = 최단거리_경로_요청(강남역.getId(), 분당역.getId());
        PathResponse 개성_강남 = 최단거리_경로_요청(개성역.getId(), 강남역.getId());
        PathResponse 잠실_잠실나루 = 최단거리_경로_요청(잠실역.getId(), 잠실나루역.getId());
        PathResponse 잠실_강변 = 최단거리_경로_요청(잠실역.getId(), 강변역.getId());

        //then
        assertThat(양재_잠실.getDistance()).isEqualTo(13);
        assertThat(양재_잠실.getStations()).containsExactly(양재역, 오리역, 선릉역, 잠실역);
        assertThat(양재_잠실.getFare()).isEqualTo(1250);

        //10km초과∼50km까지(5km마다 100원)
        assertThat(강남_분당.getFare()).isEqualTo(1450);

        //50km초과 시 (8km마다 100원) 증가한다. 1250(10km) + 800(40km) + 100(10km)
        assertThat(개성_강남.getFare()).isEqualTo(2150);

        //추가 운임이 계산된다.
        assertThat(잠실_잠실나루.getFare()).isEqualTo(2150);

        //추가요금이 있는 노선 2개를 거치는 노선을 지나갈 시 가장 높은 금액의 추가요금만 적용된다.
        assertThat(잠실_강변.getFare()).isEqualTo(2450);
    }

    @Test
    @DisplayName("실패 케이스 테스트")
    void noConnectTest() {
        //When 출발역과 도착역이 연결이 되어 있지 않은 경우
        ExtractableResponse<Response> 연결되지_않음 = 최단거리_경로_요청_응답(교대역.getId(), 은계역.getId());

        //When 출발역과 도착역이 같은 경우
        ExtractableResponse<Response> 시작_끝_같음 = 최단거리_경로_요청_응답(교대역.getId(), 교대역.getId());

        //When 존재하지 않는 출발역이나 도착역
        ExtractableResponse<Response> 구간_없음 = 최단거리_경로_요청_응답(사당역.getId(), 서울대역.getId());

        //then
        assertThat(연결되지_않음.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(시작_끝_같음.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(구간_없음.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("나이대별 요금이 다른지 테스트")
    void ageFareTest() {
        //given
        //청소년: 13세 이상~19세 미만
        //어린이: 6세 이상~ 13세 미만
        회원_생성_요청("7271kim@naver.com", "pw", 13);
        회원_생성_요청("7271kim@naver.com2", "pw", 12);

        TokenResponse 청소년 = 계정_로그인("7271kim@naver.com", "pw");
        TokenResponse 어린이 = 계정_로그인("7271kim@naver.com2", "pw");

        //when 60km구간 운행시
        PathResponse 청소년_60km = 최단거리_경로_요청(개성역.getId(), 강남역.getId(), 청소년);
        PathResponse 어린이_60km = 최단거리_경로_요청(개성역.getId(), 강남역.getId(), 어린이);

        //then then 50km초과 시 (8km마다 100원) 증가한다. 1250(10km) + 800(40km) + 100(10km)
        assertThat(청소년_60km.getFare()).isEqualTo(1720);
        assertThat(어린이_60km.getFare()).isEqualTo(1075);

    }

    private PathResponse 최단거리_경로_요청(Long sourceId, Long targetId, TokenResponse token) {
        return RestAssured
            .given().log().all()
            .auth().oauth2(token.getAccessToken())
            .when()
            .get(String.format("/paths?source=%d&target=%d", sourceId, targetId))
            .then().log().all().extract()
            .jsonPath()
            .getObject(".", PathResponse.class);
    }

    private ExtractableResponse<Response> 최단거리_경로_요청_응답(Long sourceId, Long targetId) {
        return RestAssured
            .given().log().all()
            .when()
            .get(String.format("/paths?source=%d&target=%d", sourceId, targetId))
            .then().log().all().extract();
    }

    private PathResponse 최단거리_경로_요청(Long sourceId, Long targetId) {
        return 최단거리_경로_요청_응답(sourceId, targetId)
            .jsonPath()
            .getObject(".", PathResponse.class);
    }
}
