package atdd.line.application;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import atdd.line.domain.LineRepository;
import atdd.line.dto.LineRequest;
import atdd.line.dto.LineResponse;
import atdd.station.exception.InputException;

@Service
@Transactional
public class LineService {
    private LineRepository lineRepository;

    public LineService(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }

    public LineResponse saveLine(LineRequest lineRequest) {
        validate(lineRequest);
        return LineResponse.from(lineRepository.save(lineRequest.toLine()));
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
        if (lineRequest == null || StringUtils.isBlank(lineRequest.getName()) || StringUtils.isBlank(lineRequest.getColor())) {
            throw new InputException();
        }
    }

}
