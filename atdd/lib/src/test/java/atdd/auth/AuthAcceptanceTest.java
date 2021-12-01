package atdd.auth;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import atdd.AcceptanceTest;
import atdd.auth.dto.TokenRequest;
import atdd.auth.dto.TokenResponse;
import atdd.auth.exception.AuthorizationException;
import atdd.common.ErrorResponse;
import atdd.member.MemberAcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@DisplayName("인증 관련 인수테스트")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        //given
        MemberAcceptanceTest.회원_생성_요청("7271kim@naver.com", "1234", 20);
    }

    @DisplayName("Bearer Auth - 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        //when
        ExtractableResponse<Response> response = 계정_로그인("7271kim@naver.com", "1234");

        //then
        TokenResponse token = response.jsonPath().getObject(".", TokenResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(token.getAccessToken()).isNotBlank();

    }

    @DisplayName("Bearer Auth 로그인 실패 - 비밀번호가 맞지 않음")
    @Test
    void myInfoWithBadBearerAuth() {
        ExtractableResponse<Response> response = 계정_로그인("7271kim@naver.com", "123224");

        //then
        ErrorResponse errorResponse = response.jsonPath().getObject(".", ErrorResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(errorResponse.getMessage()).isEqualTo(AuthorizationException.MESSAGE);
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {

    }

    public static ExtractableResponse<Response> 계정_로그인(String email, String password) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new TokenRequest(email, password))
            .when().post("/login/token")
            .then().log().all()
            .extract();
    }
}
