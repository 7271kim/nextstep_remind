package atdd.line.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import atdd.line.domain.LineRepository;
import atdd.line.dto.LineRequest;
import atdd.line.dto.LineResponse;

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
        // TODO Auto-generated method stub

    }

}
