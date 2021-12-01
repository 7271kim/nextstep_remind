package atdd.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import atdd.AcceptanceTest;

@DisplayName("인증 관련 인수테스트")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("Bearer Auth")
    @Test
    void myInfoWithBearerAuth() {}

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {}

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {}
}
