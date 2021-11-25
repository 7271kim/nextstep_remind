package atdd.line;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import atdd.AcceptanceTest;

@DisplayName("노선 관리 인수테스트")
public class LineAcceptantTest extends AcceptanceTest {

    @Test
    @DisplayName("지하철 노성 생성 테스트")
    void createTest() {

    }

    @Test
    @DisplayName("빈 값으로 생성 할 경우 오류확인")
    void emptyErrorTest() {

    }

    @Test
    @DisplayName("존재하는 노선을 또 추가할 경우 오류 확인")
    void alreayExsistTest() {

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

}
