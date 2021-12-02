package atdd.member;

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
import atdd.member.dto.AdminMemberRequest;
import atdd.member.dto.AdminMemberResponse;
import atdd.member.dto.MemberRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@DisplayName("회원관리 인수테스트")
public class MemberAcceptanceTest extends AcceptanceTest {

    private Long id;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        //given
        String location = 회원_생성_요청("7271kim@naver.com", "1234", 20).header("Location");
        id = Long.parseLong(location.substring(location.lastIndexOf("/") + 1));
        어드민_계정_생성();
    }

    @DisplayName("회원 생성 확인")
    @Test
    void createMember() {
        //when
        ExtractableResponse<Response> response = 회원_생성_요청("test", "test", 20);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    @DisplayName("회원 정보 확인")
    @Test
    void getMember() {
        //given
        //admin 계정 로그인
        TokenResponse token = AuthAcceptanceTest.계정_로그인("rootAdmin", "1234");

        //when
        AdminMemberResponse response = 회원_정보_조회_요청(id, token);

        //then
        assertThat(response.getId()).isEqualTo(id);
        assertThat(response.getEmail()).isEqualTo("7271kim@naver.com");
        assertThat(response.getAge()).isEqualTo(20);
        assertThat(response.getActiveType()).isEqualTo(1);
        assertThat(response.getUserType()).isEqualTo(0);
    }

    @DisplayName("회원 목록 조회")
    @Test
    void getMembers() {
        //given
        회원_생성_요청("7271kim@naver.com2", "1234", 40);
        TokenResponse token = AuthAcceptanceTest.계정_로그인("rootAdmin", "1234");

        //when
        List<AdminMemberResponse> response = 회원_목록_조회(token);

        //then
        assertThat(response.size()).isEqualTo(3);
    }

    @DisplayName("회원 정보 수정")
    @Test
    void modifyMember() {
        //given
        //admin 계정 로그인
        TokenResponse token = AuthAcceptanceTest.계정_로그인("rootAdmin", "1234");

        //when
        ExtractableResponse<Response> response = 회원_정보_수정(token, new AdminMemberRequest("aaa", "aaa", 30, 1, 1), id);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        AdminMemberResponse 신규 = 회원_정보_조회_요청(id, token);
        assertThat(신규.getEmail()).isEqualTo("aaa");
        assertThat(신규.getAge()).isEqualTo(30);
        assertThat(신규.getActiveType()).isEqualTo(1);
        assertThat(신규.getUserType()).isEqualTo(1);

    }

    @DisplayName("회원 정보 삭제")
    @Test
    void deleteMember() {
        //given
        //admin 계정 로그인
        TokenResponse token = AuthAcceptanceTest.계정_로그인("rootAdmin", "1234");

        //when
        ExtractableResponse<Response> response = 회원_삭제(token, id);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(RestAssured.given().log().all()
            .auth().oauth2(token.getAccessToken())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("/members/" + id)
            .then().log().all()
            .extract()
            .statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("admin 회원만 회원관리를 할 수 있다.")
    @Test
    void checkNomalMember() {
        //given
        //일반 계정 로그인
        TokenResponse token = AuthAcceptanceTest.계정_로그인("7271kim@naver.com", "1234");

        //when
        ExtractableResponse<Response> 조회_응답 = 회원_정보_조회_요청_응답(id, token);
        ExtractableResponse<Response> 목록_조회_응답 = 회원_목록_조회_응답(token);
        ExtractableResponse<Response> 수정_응답 = 회원_정보_수정(token, new AdminMemberRequest("aaa", "aaa", 30, 1, 1), id);
        ExtractableResponse<Response> 삭제_응답 = 회원_삭제(token, id);

        //then
        assertThat(조회_응답.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(목록_조회_응답.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(수정_응답.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(삭제_응답.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

    }

    @DisplayName("내 정보 확인")
    @Test
    void getMy() {

    }

    @DisplayName("내 정보 업데이트")
    @Test
    void updateMy() {

    }

    @DisplayName("내 정보 삭제")
    @Test
    void deleteMy() {

    }

    public static ExtractableResponse<Response> 회원_생성_요청(String email, String password, Integer age) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new MemberRequest(email, password, age))
            .when().post("/members")
            .then().log().all()
            .extract();
    }

    public static AdminMemberResponse 회원_정보_조회_요청(Long id, TokenResponse token) {
        return 회원_정보_조회_요청_응답(id, token)
            .jsonPath()
            .getObject(".", AdminMemberResponse.class);
    }

    public static ExtractableResponse<Response> 회원_정보_조회_요청_응답(Long id, TokenResponse token) {
        return RestAssured
            .given().log().all()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .auth().oauth2(token.getAccessToken())
            .when().get("/members/" + id)
            .then().log().all()
            .extract();
    }

    private static void 어드민_계정_생성() {
        RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new MemberRequest("", "1234", 20))
            .when().post("/members/admin")
            .then().log().all()
            .extract();
    }

    private List<AdminMemberResponse> 회원_목록_조회(TokenResponse token) {
        return 회원_목록_조회_응답(token)
            .jsonPath()
            .getList(".", AdminMemberResponse.class);
    }

    private ExtractableResponse<Response> 회원_목록_조회_응답(TokenResponse token) {
        return RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .auth().oauth2(token.getAccessToken())
            .when()
            .get("/members")
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> 회원_정보_수정(TokenResponse token, AdminMemberRequest request, Long id) {
        return RestAssured.given().log().all()
            .body(request)
            .auth().oauth2(token.getAccessToken())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .put("/members/" + id)
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> 회원_삭제(TokenResponse token, Long id) {
        return RestAssured.given().log().all()
            .auth().oauth2(token.getAccessToken())
            .when()
            .delete("/members/" + id)
            .then().log().all()
            .extract();
    }

}
