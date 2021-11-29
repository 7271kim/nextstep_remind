package atdd.line.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LineRepository extends JpaRepository<Line, Long> {

    Optional<Line> findByName(String name);

    @Query("SELECT distinct line FROM Line line JOIN FETCH line.sections.list section JOIN FETCH section.upstation JOIN FETCH section.downStatoin")
    List<Line> findAllJoinFetch();

}
