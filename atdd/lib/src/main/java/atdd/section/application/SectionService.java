package atdd.section.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import atdd.common.InputException;
import atdd.line.domain.Line;
import atdd.line.domain.LineRepository;
import atdd.section.domain.SectionRepository;
import atdd.section.dto.SectionRequest;
import atdd.station.domain.Station;
import atdd.station.domain.StationRepository;

@Service
@Transactional
public class SectionService {

    private SectionRepository sectionRepository;
    private LineRepository lineRepository;
    private StationRepository stationRepository;

    public SectionService(SectionRepository sectionRepository, LineRepository lineRepository,
            StationRepository stationRepository) {
        this.sectionRepository = sectionRepository;
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
    }

    public void createSection(Long lineId, SectionRequest request) {
        Line line = lineRepository.findById(lineId).orElseThrow(InputException::new);
        Station upStation = stationRepository.findById(request.getUpStationId()).orElseThrow(InputException::new);
        Station downStation = stationRepository.findById(request.getDownStationId()).orElseThrow(InputException::new);
        line.addSection(request.toSection(line, upStation, downStation));
    }

    public void delete(Long lineId, Long stationId) {
        Line line = lineRepository.findById(lineId).orElseThrow(InputException::new);
        line.deleteSection(stationId);
    }

}
