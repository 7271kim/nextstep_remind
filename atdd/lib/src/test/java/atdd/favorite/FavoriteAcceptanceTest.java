package atdd.favorite;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import atdd.AcceptanceTest;
import atdd.auth.AuthAcceptanceTest;
import atdd.auth.dto.TokenResponse;
import atdd.favorite.dto.FavoriteRequest;
import atdd.favorite.dto.FavoriteResponse;
import atdd.line.LineAcceptantTest;
import atdd.line.dto.LineResponse;
import atdd.member.MemberAcceptanceTest;
import atdd.section.SectionAcceptantTest;
import atdd.station.StationAcceptantTest;
import atdd.station.dto.StationResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@DisplayName("회원관리 인수테스트")
public class FavoriteAcceptanceTest extends AcceptanceTest {

    private StationResponse 강남역;
    private StationResponse 교대역;
    private StationResponse 역삼역;
    private LineResponse 이호선;
    private TokenResponse token;
    private long id;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        //given
        강남역 = StationAcceptantTest.지하철역_생성요청("강남역");
        교대역 = StationAcceptantTest.지하철역_생성요청("교대역");
        역삼역 = StationAcceptantTest.지하철역_생성요청("역삼역");
        이호선 = LineAcceptantTest.지하철_노선_생성요청("bg-red-600", "2호선", 강남역.getId(), 교대역.getId(), 100, 1000);
        SectionAcceptantTest.지하철_구간_생성요청(교대역.getId(), 역삼역.getId(), 10, 이호선.getId());

        MemberAcceptanceTest.회원_생성_요청("7271kim@naver.com", "1234", 20);
        MemberAcceptanceTest.회원_생성_요청("7271kim@naver2.com", "1234", 20);
        token = AuthAcceptanceTest.계정_로그인("7271kim@naver.com", "1234");
        TokenResponse token2 = AuthAcceptanceTest.계정_로그인("7271kim@naver2.com", "1234");
        즐겨찾기_생성_요청_응답(강남역.getId(), 역삼역.getId(), token2);

        String location = 즐겨찾기_생성_요청_응답(강남역.getId(), 역삼역.getId(), token).header("Location");
        id = Long.parseLong(location.substring(location.lastIndexOf("/") + 1));
    }

    @DisplayName("즐겨찾기 생성 확인")
    @Test
    void createFavorite() {
        //when
        ExtractableResponse<Response> response = 즐겨찾기_생성_요청_응답(강남역.getId(), 교대역.getId(), token);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    @DisplayName("없는 역으로 즐겨찾기 생성 시 에러 출력")
    @Test
    void createErrorFavorite() {
        //when
        ExtractableResponse<Response> response = 즐겨찾기_생성_요청_응답(5l, 6l, token);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("즐겨찾기 목록 조회")
    @Test
    void getFavorites() {
        //when
        List<FavoriteResponse> response = 즐겨찾기_목록_조회(token);

        //then
        assertThat(response.size()).isEqualTo(1);
        assertThat(response.get(0).getSource().getName()).isEqualTo("강남역");
        assertThat(response.get(0).getTarget().getName()).isEqualTo("역삼역");

    }

    @DisplayName("즐겨찾기 삭제")
    @Test
    void deleteFavorite() {
        //when
        ExtractableResponse<Response> response = 즐겨찾기_삭제_응답(token, id);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(즐겨찾기_목록_조회(token).size()).isZero();
    }

    @DisplayName("잘못된 즐겨 찾기 삭제")
    @Test
    void deleteErrorFavorite() {
        //when
        ExtractableResponse<Response> response = 즐겨찾기_삭제_응답(token, 5l);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    public static ExtractableResponse<Response> 즐겨찾기_생성_요청_응답(Long source, Long target, TokenResponse token) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .auth().oauth2(token.getAccessToken())
            .body(new FavoriteRequest(source, target))
            .when().post("/favorites")
            .then().log().all()
            .extract();
    }

    private List<FavoriteResponse> 즐겨찾기_목록_조회(TokenResponse token) {
        // TODO Auto-generated method stub
        return RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .auth().oauth2(token.getAccessToken())
            .when()
            .get("/favorites")
            .then().log().all()
            .extract()
            .jsonPath()
            .getList(".", FavoriteResponse.class);
    }

    private ExtractableResponse<Response> 즐겨찾기_삭제_응답(TokenResponse token, Long id) {
        return RestAssured.given().log().all()
            .auth().oauth2(token.getAccessToken())
            .when()
            .delete("/favorites/" + id)
            .then().log().all()
            .extract();
    }

}
