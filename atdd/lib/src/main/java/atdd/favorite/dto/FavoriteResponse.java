package atdd.favorite.dto;

import atdd.favorite.domain.Favorite;
import atdd.station.dto.StationResponse;

public class FavoriteResponse {
    private Long id;
    private StationResponse source;
    private StationResponse target;

    public FavoriteResponse() {}

    public FavoriteResponse(Long id, StationResponse source, StationResponse target) {
        this.id = id;
        this.source = source;
        this.target = target;
    }

    public Long getId() {
        return id;
    }

    public StationResponse getSource() {
        return source;
    }

    public StationResponse getTarget() {
        return target;
    }

    public static FavoriteResponse of(Favorite saved) {
        return new FavoriteResponse(saved.getId(), StationResponse.from(saved.getSource()), StationResponse.from(saved.getTarget()));
    }
}
