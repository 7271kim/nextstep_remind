package atdd.favorite.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import atdd.station.domain.Station;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByMemberIdAndSourceAndTarget(Long memberId, Station Source, Station target);

    @Query("SELECT distinct favorite FROM Favorite favorite JOIN FETCH favorite.source JOIN FETCH favorite.target WHERE favorite.memberId=:loginId")
    List<Favorite> findByMemberId(@Param("loginId") Long loginId);

    Optional<Favorite> findByIdAndMemberId(Long favoriteId, Long loginId);

}
