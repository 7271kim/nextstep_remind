package atdd.line;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import atdd.AcceptanceTest;
import atdd.common.ErrorResponse;
import atdd.line.dto.LineRequest;
import atdd.line.dto.LineResponse;
import atdd.section.SectionAcceptantTest;
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
    private StationResponse 역삼역;
    private LineResponse 이호선;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        //given
        강남역 = StationAcceptantTest.지하철역_생성요청("강남역");
        교대역 = StationAcceptantTest.지하철역_생성요청("교대역");
        역삼역 = StationAcceptantTest.지하철역_생성요청("역삼역");
        이호선 = 지하철_노선_생성요청("bg-red-600", "2호선", 강남역.getId(), 교대역.getId(), 100);
        SectionAcceptantTest.지하철_구간_생성요청(교대역.getId(), 역삼역.getId(), 10, 이호선.getId());
    }

    @Test
    @DisplayName("지하철 노성 생성 테스트")
    void createTest() {
        //when
        ExtractableResponse<Response> response = 지하철_노선_생성요청(
            new LineRequest("bg-red-600", "신분당선", 강남역.getId(), 교대역.getId(), 100));

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
        ExtractableResponse<Response> response = 지하철_노선_생성요청(
            new LineRequest("bg-red-600", "2호선", 강남역.getId(), 교대역.getId(), 100));

        //then
        ErrorResponse errorResponse = response.jsonPath().getObject(".", ErrorResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(InputException.MESSAGE);
    }

    @Test
    @DisplayName("지하철 노선 목록 조회 테스트")
    void showListTest() {
        //given
        지하철_노선_생성요청("블루", "신분당선", 역삼역.getId(), 강남역.getId(), 100);

        //when
        List<LineResponse> response = RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("/lines")
            .then().log().all()
            .extract()
            .jsonPath()
            .getList(".", LineResponse.class);

        LineResponse 이호선_응답 = response.get(0);
        LineResponse 신분당선_응답 = response.get(1);

        //then
        assertThat(response.size()).isEqualTo(2);
        assertThat(이호선_응답.getId()).isEqualTo(이호선.getId());
        assertThat(이호선_응답.getName()).isEqualTo(이호선.getName());
        assertThat(이호선_응답.getColor()).isEqualTo(이호선.getColor());
        assertThat(이호선_응답.getStations().stream()
            .map(StationResponse::getName)
            .collect(Collectors.toList())).containsExactly("강남역", "교대역", "역삼역");
        assertThat(신분당선_응답.getStations().stream()
            .map(StationResponse::getName)
            .collect(Collectors.toList())).containsExactly("역삼역", "강남역");
    }

    @Test
    @DisplayName("지하철 특정 노선 조회")
    void showLineTest() {
        //when
        LineResponse response = 지하철_노선_조회(이호선.getId());

        //then
        assertThat(response.getId()).isEqualTo(이호선.getId());
        assertThat(response.getStations().stream()
            .map(StationResponse::getName)
            .collect(Collectors.toList())).containsExactly("강남역", "교대역", "역삼역");
    }

    @Test
    @DisplayName("지하철 노성 수정 확인")
    void modifyTest() {
        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .body(LineRequest.of("bg-blue-600", "구분당선"))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .put("/lines/" + 이호선.getId())
            .then().log().all()
            .extract();
        //then

        LineResponse line = 지하철_노선_조회(이호선.getId());
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(line.getColor()).isEqualTo("bg-blue-600");
        assertThat(line.getName()).isEqualTo("구분당선");
    }

    @Test
    @DisplayName("존재 하지 않는 노선 수정 시 오류 확인")
    void emptyLineModifyErrorTest() {
        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .body(LineRequest.of("bg-blue-600", "구분당선"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put("/lines/7")
                .then().log().all()
                .extract();

        //then
        ErrorResponse errorResponse = response.jsonPath().getObject(".", ErrorResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(InputException.MESSAGE);
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

    private LineResponse 지하철_노선_조회(Long lineId) {
        return RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("/lines/" + lineId)
            .then().log().all()
            .extract()
            .jsonPath()
            .getObject(".", LineResponse.class);
    }
}
