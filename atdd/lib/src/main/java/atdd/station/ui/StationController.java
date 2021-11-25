package atdd.station.ui;

import java.net.URI;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import atdd.station.dto.StationRequest;
import atdd.station.dto.StationResponse;
import atdd.station.exception.InputException;

@RestController
public class StationController {

    @PostMapping("/stations")
    public ResponseEntity<StationResponse> createStation(@RequestBody
    StationRequest stationRequest) {
        StationResponse temp = new StationResponse(1, stationRequest.getName(), LocalDateTime.now(), LocalDateTime.now());
        if (stationRequest.getName() == null) {
            throw new InputException();
        }
        return ResponseEntity.created(URI.create("/stations/" + temp.getId())).body(temp);
    }
}
