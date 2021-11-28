package atdd.section;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import atdd.AcceptanceTest;
import atdd.common.ErrorResponse;
import atdd.common.InputException;
import atdd.line.LineAcceptantTest;
import atdd.line.dto.LineResponse;
import atdd.section.dto.SectionRequest;
import atdd.section.exception.AlreadyExistUpDownStationException;
import atdd.station.StationAcceptantTest;
import atdd.station.dto.StationResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@DisplayName("지하철 구간 관리 인수테스트")
public class SectionAcceptantTest extends AcceptanceTest {

    private StationResponse 강남역;
    private StationResponse 교대역;
    private StationResponse 서초역;

    private LineResponse 이호선;
    private StationResponse 역삼역;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        //given
        강남역 = StationAcceptantTest.지하철역_생성요청("강남역");
        교대역 = StationAcceptantTest.지하철역_생성요청("교대역");
        서초역 = StationAcceptantTest.지하철역_생성요청("서초역");
        역삼역 = StationAcceptantTest.지하철역_생성요청("역삼역");

        이호선 = LineAcceptantTest.지하철_노선_생성요청("bg-red-600", "2호선", 강남역.getId(), 교대역.getId(), 100);
    }

    @Test
    @DisplayName("지하철 구간 등록 테스트")
    void createTest() {
        //when

        ExtractableResponse<Response> response = 지하철_구간_생성요청(new SectionRequest(강남역.getId(), 서초역.getId(), 10), 이호선.getId());

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("기존 존재하는 구간으로 등록 시 오류 출력")
    void createExistTest() {
        //when
        ExtractableResponse<Response> response = 지하철_구간_생성요청(new SectionRequest(강남역.getId(), 교대역.getId(), 10), 이호선.getId());
        //then
        ErrorResponse errorResponse = response.jsonPath().getObject(".", ErrorResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(AlreadyExistUpDownStationException.MESSAGE);
    }

    @Test
    @DisplayName("상행성 하행선 둘다 매칭되지 않을 때 오류 출력")
    void UpDownNoExistTest() {
        //given
        StationResponse 이대역 = StationAcceptantTest.지하철역_생성요청("이대역");
        StationResponse 신촌역 = StationAcceptantTest.지하철역_생성요청("신촌역");

        //when
        ExtractableResponse<Response> response = 지하철_구간_생성요청(new SectionRequest(이대역.getId(), 신촌역.getId(), 10), 이호선.getId());
        //then
        ErrorResponse errorResponse = response.jsonPath().getObject(".", ErrorResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(InputException.MESSAGE);
    }

    @Test
    @DisplayName("존재하지 않는 역으로 구간 등록 시 오류를 출력한다.")
    void createWithEmptyStationTest() {
        //when
        ExtractableResponse<Response> response = 지하철_구간_생성요청(new SectionRequest(4l, 5l, 10), 이호선.getId());

        //then
        ErrorResponse errorResponse = response.jsonPath().getObject(".", ErrorResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(InputException.MESSAGE);
    }

    @Test
    @DisplayName("존재하지 않는 호선으로 구간 등록 시 오류를 출력한다.")
    void createWithEmptyLineTest() {
        ExtractableResponse<Response> response = 지하철_구간_생성요청(new SectionRequest(강남역.getId(), 교대역.getId(), 10), 5l);

        //then
        ErrorResponse errorResponse = response.jsonPath().getObject(".", ErrorResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(InputException.MESSAGE);
    }

    @Test
    @DisplayName("구간 삭제 테스트")
    void deleteTest() {
        //given
        지하철_구간_생성요청(강남역.getId(), 교대역.getId(), 100, 이호선.getId());

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .when()
            .delete(String.format("/lines/%d/sections?stationId=%d", 이호선.getId(), 강남역.getId()))
            .then().log().all()
            .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("존재하지 않는 역으로 구간 삭제 시 오류를 출력한다.")
    void deleteWithEmptyStationTest() {
        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .when()
            .delete(String.format("/lines/%d/sections?stationId=%d", 이호선.getId(), 10))
            .then().log().all()
            .extract();

        //then
        ErrorResponse errorResponse = response.jsonPath().getObject(".", ErrorResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(InputException.MESSAGE);
    }

    @Test
    @DisplayName("역과 역 사이 구간 추가")
    void 구간_사이추가() {
        //강남 - 서초역 - 교대역 - 역삼역
        //when
        지하철_구간_생성요청(강남역.getId(), 서초역.getId(), 50, 이호선.getId());
        지하철_구간_생성요청(교대역.getId(), 역삼역.getId(), 100, 이호선.getId());

        //then
        assertThat(LineAcceptantTest.지하철_노선의_지하철역_조회(이호선.getId())).containsExactly("강남역", "서초역", "교대역", "역삼역");

    }

    @Test
    @DisplayName("존재하지 않는 노선으로 구간 삭제 시 오류를 출력한다.")
    void deleteWithEmptylineTest() {
        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .when()
            .delete(String.format("/lines/%d/sections?stationId=%d", 10, 강남역.getId()))
            .then().log().all()
            .extract();

        //then
        ErrorResponse errorResponse = response.jsonPath().getObject(".", ErrorResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(InputException.MESSAGE);
    }

    private ExtractableResponse<Response> 지하철_구간_생성요청(SectionRequest sectionRequest, Long lineId) {
        return RestAssured.given().log().all()
            .body(sectionRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post(String.format("/lines/%d/sections", lineId))
            .then().log().all()
            .extract();
    }

    public static void 지하철_구간_생성요청(Long upstationId, Long downStationId, int distance, Long lineId) {
        RestAssured.given().log().all()
            .body(SectionRequest.of(upstationId, downStationId, distance))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post(String.format("/lines/%d/sections", lineId))
            .then().log().all()
            .extract();
    }
}
