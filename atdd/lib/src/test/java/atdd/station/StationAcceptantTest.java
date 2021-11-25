package atdd.station;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import atdd.AcceptanceTest;
import atdd.common.ErrorResponse;
import atdd.station.dto.StationRequest;
import atdd.station.dto.StationResponse;
import atdd.station.exception.InputException;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@DisplayName("Feature : 지하철 역 관리 인수테스트")
public class StationAcceptantTest extends AcceptanceTest {

    @Test
    @DisplayName("Scenario : 지하철 역 생성")
    void creatStation() {
        //when
        ExtractableResponse<Response> response = 지하철역_생성요청(StationRequest.of("강남역"));

        //then
        StationResponse staion = response.jsonPath().getObject(".", StationResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
        assertThat(staion.getName()).isEqualTo("강남역");
        assertThat(staion.getCreatedDate()).isNotNull();
        assertThat(staion.getModifiedDate()).isNotNull();
    }

    @Test
    @DisplayName("이름 없이 생성시 실패 한다.")
    void noNameTest() {
        //when
        ExtractableResponse<Response> response = 지하철역_생성요청(new StationRequest());

        //then
        ErrorResponse errorResponse = response.jsonPath().getObject(".", ErrorResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(InputException.MESSAGE);
    }

    @Test
    @DisplayName("기존에 존재하는 역이름으로 생성 시 실패한다.")
    void alreadyNameTest() {
        //givn
        지하철역_생성요청("강남역");

        //when
        ExtractableResponse<Response> response = 지하철역_생성요청(StationRequest.of("강남역"));

        //then
        ErrorResponse errorResponse = response.jsonPath().getObject(".", ErrorResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(InputException.MESSAGE);
    }

    @Test
    @DisplayName("지하철 역 목록 조회")
    void getStationsTest() {
        //given
        지하철역_생성요청(StationRequest.of("강남역"));
        지하철역_생성요청(StationRequest.of("교대역"));

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .when()
            .get("/stations")
            .then().log().all()
            .extract();

        //then
        List<String> nameList = response.jsonPath().getList(".", StationResponse.class)
            .stream()
            .map(StationResponse::getName)
            .collect(Collectors.toList());
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(nameList).contains("강남역", "교대역");
    }

    @Test
    @DisplayName("지하철 역 정상 삭제 확인")
    void deleteTest() {
        //given
        Long id = 지하철역_생성요청("강남역").getId();

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .when()
            .delete("/stations/" + id)
            .then().log().all()
            .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("없는역 제거시 에러 발생")
    void validateDeletionTest() {
        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .when()
            .delete("/station/0")
            .then().log().all()
            .extract();

        //then
        ErrorResponse errorResponse = response.jsonPath().getObject(".", ErrorResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(InputException.MESSAGE);
    }

    public static StationResponse 지하철역_생성요청(String name) {
        return RestAssured.given().log().all()
            .body(StationRequest.of(name))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/stations")
            .then().log().all()
            .extract()
            .jsonPath()
            .getObject(".", StationResponse.class);
    }

    public static ExtractableResponse<Response> 지하철역_생성요청(StationRequest stationRequest) {
        return RestAssured.given().log().all()
            .body(stationRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/stations")
            .then().log().all()
            .extract();
    }

}
