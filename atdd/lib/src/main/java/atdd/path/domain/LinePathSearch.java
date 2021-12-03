package atdd.path.domain;

import java.util.List;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;

import atdd.common.InputException;
import atdd.section.domain.Section;
import atdd.station.domain.Station;
import io.jsonwebtoken.lang.Collections;

public class LinePathSearch {

    private WeightedMultigraph<Station, SectionEdge> graph = new WeightedMultigraph<>(SectionEdge.class);
    private DijkstraShortestPath<Station, SectionEdge> dijkstra;

    public LinePathSearch(List<Section> sections) {
        validate(sections);
        settingGraphAndMaxFee(sections);
        this.dijkstra = new DijkstraShortestPath<>(graph);
    }

    private void validate(List<Section> sections) {
        if (Collections.isEmpty(sections)) {
            throw new InputException();
        }
    }

    private void settingGraphAndMaxFee(List<Section> sections) {
        for (Section section : sections) {
            Station upStation = section.getUpstation();
            Station downStation = section.getDownStatoin();
            SectionEdge edge = SectionEdge.of(section);
            graph.addVertex(upStation);
            graph.addVertex(downStation);
            graph.addEdge(upStation, downStation, edge);
            graph.setEdgeWeight(edge, section.getDistance());
        }
    }

    public static LinePathSearch of(List<Section> sections) {
        return new LinePathSearch(sections);
    }

    public LinePath searchPath(Station startStation, Station endStation) {
        validateStation(startStation, endStation);
        return LinePath.of(dijkstra.getPath(startStation, endStation));
    }

    private void validateStation(Station startStation, Station endStation) {
        if (startStation.equals(endStation)) {
            throw new InputException();
        }

        if (!graph.containsVertex(startStation) || !graph.containsVertex(endStation)) {
            throw new InputException();
        }
    }

}
