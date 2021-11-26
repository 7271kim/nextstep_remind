package atdd.section.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import atdd.line.domain.Line;
import atdd.line.domain.LineRepository;
import atdd.section.domain.Section;
import atdd.section.domain.SectionRepository;
import atdd.section.dto.SectionRequest;
import atdd.station.domain.Station;
import atdd.station.domain.StationRepository;
import atdd.station.exception.InputException;

@Service
@Transactional
public class SectionService {

    private SectionRepository sectionRepository;
    private LineRepository lineRepository;
    private StationRepository stationRepository;

    public SectionService(SectionRepository sectionRepository, LineRepository lineRepository, StationRepository stationRepository) {
        this.sectionRepository = sectionRepository;
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
    }

    public void createSection(Long lineId, SectionRequest request) {
        Line line = lineRepository.findById(lineId).orElseThrow(InputException::new);
        Station upStation = stationRepository.findById(request.getUpStationId()).orElseThrow(InputException::new);
        Station downStation = stationRepository.findById(request.getDownStationId()).orElseThrow(InputException::new);
        Section section = sectionRepository.save(request.toSection(line, upStation, downStation));
        line.addSection(section);
    }

    public void delete(Long lineId, Long stationId) {
        Line line = lineRepository.findById(lineId).orElseThrow(InputException::new);
        line.deleteSection(stationId);
    }

}
