package atdd.section;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import atdd.AcceptanceTest;

@DisplayName("지하철 구간 관리 인수테스트")
public class SectionAcceptantTest extends AcceptanceTest {

    @Test
    @DisplayName("지하철 구간 등록 테스트")
    void createTest() {

    }

    @Test
    @DisplayName("존재하지 않는 역으로 구간 등록 시 오류를 출력한다.")
    void createWithEmptyStationTest() {

    }

    @Test
    @DisplayName("구간 삭제 테스트")
    void deleteTest() {

    }

    @Test
    @DisplayName("존재하지 않는 역으로 구간 등록 시 오류를 출력한다.")
    void deleteWithEmptyStationTest() {

    }
}
