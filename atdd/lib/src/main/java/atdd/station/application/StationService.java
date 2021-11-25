package atdd.station.application;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<StationResponse> findAllStation() {
        return stationRepository.findAll().stream()
            .map(StationResponse::from)
            .collect(Collectors.toList());
    }

    public void deleteStationById(Long id) {
        validateDeletion(id);
        stationRepository.deleteById(id);
    }

    private void validateDeletion(Long id) {
        stationRepository.findById(id).orElseThrow(InputException::new);

    }

}
