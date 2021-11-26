package atdd.section;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import atdd.AcceptanceTest;
import atdd.common.ErrorResponse;
import atdd.line.LineAcceptantTest;
import atdd.line.dto.LineResponse;
import atdd.section.dto.SectionRequest;
import atdd.station.StationAcceptantTest;
import atdd.station.dto.StationResponse;
import atdd.station.exception.InputException;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@DisplayName("지하철 구간 관리 인수테스트")
public class SectionAcceptantTest extends AcceptanceTest {

    private StationResponse 강남역;
    private StationResponse 교대역;
    private LineResponse 이호선;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        //given
        강남역 = StationAcceptantTest.지하철역_생성요청("강남역");
        교대역 = StationAcceptantTest.지하철역_생성요청("교대역");
        이호선 = LineAcceptantTest.지하철_노선_생성요청("bg-red-600", "2호선", 강남역.getId(), 교대역.getId(), 100);
    }

    @Test
    @DisplayName("지하철 구간 등록 테스트")
    void createTest() {
        //when
        ExtractableResponse<Response> response = 지하철_구간_생성요청(new SectionRequest(강남역.getId(), 교대역.getId(), 10), 이호선.getId());

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
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

    }

    @Test
    @DisplayName("존재하지 않는 역으로 구간 등록 시 오류를 출력한다.")
    void deleteWithEmptyStationTest() {

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
            .extract()
            .jsonPath()
            .getObject(".", LineResponse.class);
    }
}
