package atdd.line;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import atdd.AcceptanceTest;
import atdd.common.ErrorResponse;
import atdd.line.dto.LineRequest;
import atdd.line.dto.LineResponse;
import atdd.station.StationAcceptantTest;
import atdd.station.dto.StationResponse;
import atdd.station.exception.InputException;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@DisplayName("노선 관리 인수테스트")
public class LineAcceptantTest extends AcceptanceTest {

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
        이호선 = 지하철_노선_생성요청("bg-red-600", "2호선", 강남역.getId(), 교대역.getId(), 100);
    }

    @Test
    @DisplayName("지하철 노성 생성 테스트")
    void createTest() {
        //when
        ExtractableResponse<Response> response = 지하철_노선_생성요청(new LineRequest("bg-red-600", "신분당선", 강남역.getId(), 교대역.getId(), 100));

        //then
        LineResponse line = response.jsonPath().getObject(".", LineResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
        assertThat(line.getId()).isNotNull();
        assertThat(line.getName()).isEqualTo("신분당선");
        assertThat(line.getColor()).isEqualTo("bg-red-600");
        assertThat(line.getCreatedDate()).isNotNull();
        assertThat(line.getModifiedDate()).isNotNull();

    }

    @Test
    @DisplayName("빈 값으로 노선 생성 할 경우 오류 출력")
    void emptyErrorTest() {
        //when
        ExtractableResponse<Response> response = 지하철_노선_생성요청(new LineRequest());

        //then
        ErrorResponse errorResponse = response.jsonPath().getObject(".", ErrorResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(InputException.MESSAGE);
    }

    @Test
    @DisplayName("존재하는 노선을 또 추가할 경우 오류 출력")
    void alreayExsistTest() {
        //when
        ExtractableResponse<Response> response = 지하철_노선_생성요청(new LineRequest("bg-red-600", "2호선", 강남역.getId(), 교대역.getId(), 100));

        //then
        ErrorResponse errorResponse = response.jsonPath().getObject(".", ErrorResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(InputException.MESSAGE);
    }

    @Test
    @DisplayName("지하철 노선 목록 조회 테스트")
    void showListTest() {

    }

    @Test
    @DisplayName("지하철 특정 노선 조회")
    void showLineTest() {

    }

    @Test
    @DisplayName("지하철 노성 수정 확인")
    void modifyTest() {

    }

    @Test
    @DisplayName("존재 하지 않는 노선 수정 시 오류 확인")
    void emptyLineModifyErrorTest() {

    }

    @Test
    @DisplayName("지하철 노선 제거 확인")
    void deleteTest() {

    }

    @Test
    @DisplayName("존재 하지 않는 노선 삭제 시 오류 확인")
    void emptyLineRemoveErrorTest() {

    }

    private static ExtractableResponse<Response> 지하철_노선_생성요청(LineRequest lineRequest) {
        return RestAssured.given().log().all()
            .body(lineRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/lines")
            .then().log().all()
            .extract();
    }

    public static LineResponse 지하철_노선_생성요청(String color, String name, Long upStation, Long downStation, int distance) {
        return RestAssured.given().log().all()
            .body(LineRequest.of(color, name, upStation, downStation, distance))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/lines")
            .then().log().all()
            .extract()
            .jsonPath()
            .getObject(".", LineResponse.class);
    }
}