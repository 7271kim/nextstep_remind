package atdd.favorite.ui;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import atdd.auth.domain.AuthenticationPrincipal;
import atdd.favorite.application.FavoriteService;
import atdd.favorite.dto.FavoriteRequest;
import atdd.favorite.dto.FavoriteResponse;
import atdd.member.domain.Member;

@RestController
public class FavoriteController {
    private FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/favorites")
    public ResponseEntity createFavorite(@AuthenticationPrincipal Member loginMember, @RequestBody FavoriteRequest request) {
        FavoriteResponse favorite = favoriteService.createFavorite(request, loginMember.getId());
        return ResponseEntity.created(URI.create("/favorites/" + favorite.getId())).build();
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<FavoriteResponse>> getFavorites(@AuthenticationPrincipal Member loginMember,
            @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok().body(favoriteService.findAll(loginMember.getId(), pageable));
    }

    @DeleteMapping("/favorites/{favoriteId}")
    public ResponseEntity deleteMemberOfMine(@AuthenticationPrincipal Member loginMember, @PathVariable Long favoriteId) {
        favoriteService.deleteFavorite(favoriteId, loginMember.getId());
        return ResponseEntity.noContent().build();
    }
}
