package atdd.line.application;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import atdd.common.InputException;
import atdd.line.domain.Line;
import atdd.line.domain.LineRepository;
import atdd.line.dto.LineRequest;
import atdd.line.dto.LineResponse;
import atdd.section.domain.Section;
import atdd.section.domain.SectionRepository;
import atdd.station.domain.Station;
import atdd.station.domain.StationRepository;

@Service
@Transactional
public class LineService {

    private LineRepository lineRepository;
    private SectionRepository sectionRepository;
    private StationRepository stationRepository;

    public LineService(LineRepository lineRepository, SectionRepository sectionRepository,
            StationRepository stationRepository) {
        this.lineRepository = lineRepository;
        this.sectionRepository = sectionRepository;
        this.stationRepository = stationRepository;
    }

    public LineResponse saveLine(LineRequest lineRequest) {
        validate(lineRequest);
        Line line = lineRepository.save(lineRequest.toLine());
        Station upStation = stationRepository.findById(lineRequest.getUpStationId()).orElseThrow(InputException::new);
        Station donwStation = stationRepository.findById(lineRequest.getDownStationId())
            .orElseThrow(InputException::new);
        Section section = sectionRepository.save(new Section(line, upStation, donwStation, lineRequest.getDistance()));
        line.addSection(section);
        return LineResponse.from(line);
    }

    private void validate(LineRequest lineRequest) {
        emptyCheck(lineRequest);
        alreadyLineCheck(lineRequest);
    }

    private void alreadyLineCheck(LineRequest lineRequest) {
        lineRepository.findByName(lineRequest.getName()).ifPresent(line -> {
            throw new InputException();
        });
    }

    private void emptyCheck(LineRequest lineRequest) {
        if (lineRequest == null || StringUtils.isBlank(lineRequest.getName())
            || StringUtils.isBlank(lineRequest.getColor())) {
            throw new InputException();
        }
    }

    public List<LineResponse> allLines() {
        return lineRepository.findAllJoinFetch().stream().map(LineResponse::from).collect(Collectors.toList());
    }

    public LineResponse getLine(Long lineId) {
        return LineResponse.from(lineRepository.findById(lineId).orElseThrow(InputException::new));
    }

    public void updateLine(Long lineId, LineRequest request) {
        Line line = lineRepository.findById(lineId).orElseThrow(InputException::new);
        line.update(request.getColor(), request.getName());
    }

    public void deleteLine(Long lineId) {
        Line line = lineRepository.findById(lineId).orElseThrow(InputException::new);
        lineRepository.delete(line);
    }

}
