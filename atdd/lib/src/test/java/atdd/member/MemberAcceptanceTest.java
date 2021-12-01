package atdd.member;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import atdd.AcceptanceTest;
import atdd.member.dto.MemberRequest;
import atdd.member.dto.MemberResponse;
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
        //when
        MemberResponse response = 회원_정보_조회_요청(id);

        //then
        assertThat(response.getId()).isEqualTo(id);
        assertThat(response.getEmail()).isEqualTo("7271kim@naver.com");
        assertThat(response.getAge()).isEqualTo(20);
    }

    @DisplayName("회원 목록 조회")
    @Test
    void getMembers() {
        //given
        회원_생성_요청("7271kim@naver.com2", "1234", 40);

        //when
        List<MemberResponse> response = RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("/members")
            .then().log().all()
            .extract()
            .jsonPath()
            .getList(".", MemberResponse.class);

        //then
        assertThat(response.size()).isEqualTo(2);
    }

    @DisplayName("회원 정보 수정")
    @Test
    void modifyMember() {
        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .body(new MemberRequest("aaa", "aaa", 30))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .put("/members/" + id)
            .then().log().all()
            .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        MemberResponse 신규 = 회원_정보_조회_요청(id);
        assertThat(신규.getEmail()).isEqualTo("aaa");
        assertThat(신규.getAge()).isEqualTo(30);

    }

    @DisplayName("회원 정보 삭제")
    @Test
    void deleteMember() {
        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .when()
            .delete("/members/" + id)
            .then().log().all()
            .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("/members/" + id)
            .then().log().all()
            .extract()
            .statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
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

    public static MemberResponse 회원_정보_조회_요청(Long id) {
        return RestAssured
            .given().log().all()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/members/" + id)
            .then().log().all()
            .extract()
            .jsonPath()
            .getObject(".", MemberResponse.class);
    }

}
