package atdd.station.application;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import atdd.station.domain.StationRepository;
import atdd.station.dto.StationRequest;
import atdd.station.dto.StationResponse;
import atdd.station.exception.InputException;

@Service
@Transactional
public class StationService {

    private StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public StationResponse saveStation(StationRequest stationRequest) {
        validate(stationRequest);
        return StationResponse.from(stationRepository.save(stationRequest.toStation()));
    }

    private void validate(StationRequest stationRequest) {
        emptyCheck(stationRequest);
        alreadyStation(stationRequest);
    }

    private void alreadyStation(StationRequest stationRequest) {
        stationRepository.findByName(stationRequest.getName()).ifPresent(station -> {
            throw new InputException();
        });
    }

    private void emptyCheck(StationRequest stationRequest) {
        if (stationRequest == null || StringUtils.isBlank(stationRequest.getName())) {
            throw new InputException();
        }
    }

}
