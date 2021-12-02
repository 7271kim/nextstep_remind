package atdd.path.ui;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import atdd.auth.domain.NoneAuthenticationPrincipal;
import atdd.member.domain.Member;
import atdd.path.application.PathService;
import atdd.path.dto.PathResponse;

@RestController
public class PathController {

    private PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping(value = "/paths", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PathResponse> showStations(@NoneAuthenticationPrincipal Member loginMember, @RequestParam Long source, @RequestParam Long target) {
        return ResponseEntity.ok().body(pathService.findMinPath(loginMember, source, target));
    }

}