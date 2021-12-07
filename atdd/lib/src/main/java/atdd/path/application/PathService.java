package atdd.path.application;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import atdd.common.InputException;
import atdd.line.domain.LineRepository;
import atdd.member.domain.Member;
import atdd.path.domain.LinePath;
import atdd.path.domain.LinePathSearch;
import atdd.path.dto.PathResponse;
import atdd.section.domain.Section;
import atdd.section.domain.SectionRepository;
import atdd.station.domain.Station;
import atdd.station.domain.StationRepository;

@Service
@Transactional
public class PathService {
    private final LineRepository lineRepository;
    private final StationRepository stationRepository;
    private final SectionRepository sectionRepository;

    public PathService(LineRepository lineRepository, StationRepository stationRepository, SectionRepository sectionRepository) {
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
        this.sectionRepository = sectionRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "path", key = "#source+'_'+#target+'_'+#loginMember.id")
    public PathResponse findMinPath(Member loginMember, Long source, Long target) {
        Station startStation = stationRepository.findById(source).orElseThrow(InputException::new);
        Station targetStation = stationRepository.findById(target).orElseThrow(InputException::new);
        List<Section> allSections = sectionRepository.findAll();
        LinePath lineSearch = LinePathSearch.of(allSections).searchPath(startStation, targetStation);
        return PathResponse.of(lineSearch.getStations(), lineSearch.getMinDistance(), lineSearch.getFare(loginMember));
    }

}
