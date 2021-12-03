package atdd.favorite.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import atdd.common.InputException;
import atdd.favorite.domain.Favorite;
import atdd.favorite.domain.FavoriteRepository;
import atdd.favorite.dto.FavoriteRequest;
import atdd.favorite.dto.FavoriteResponse;
import atdd.station.domain.Station;
import atdd.station.domain.StationRepository;

@Service
@Transactional
public class FavoriteService {

    FavoriteRepository favoriteRepository;
    StationRepository stationRepository;

    public FavoriteService(FavoriteRepository favoriteRepository, StationRepository stationRepository) {
        this.favoriteRepository = favoriteRepository;
        this.stationRepository = stationRepository;
    }

    public FavoriteResponse createFavorite(FavoriteRequest request, Long loginId) {
        Station source = stationRepository.findById(request.getSource()).orElseThrow(InputException::new);
        Station target = stationRepository.findById(request.getTarget()).orElseThrow(InputException::new);
        favoriteRepository.findByMemberIdAndSourceAndTarget(loginId, source, target).ifPresent(favorite -> {
            throw new InputException();
        });
        Favorite saved = favoriteRepository.save(new Favorite(source, target, loginId));
        return FavoriteResponse.of(saved);
    }

    @Transactional(readOnly = true)
    public List<FavoriteResponse> findAll(Long loginId) {
        return favoriteRepository.findByMemberId(loginId).stream()
            .map(FavoriteResponse::of)
            .collect(Collectors.toList());
    }

    public void deleteFavorite(Long favoriteId, Long loginId) {
        Favorite saved = favoriteRepository.findByIdAndMemberId(favoriteId, loginId).orElseThrow(InputException::new);
        favoriteRepository.delete(saved);
    }

}
