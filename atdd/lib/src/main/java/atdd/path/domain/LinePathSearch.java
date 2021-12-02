package atdd.path.domain;

import java.util.List;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import atdd.common.InputException;
import atdd.section.domain.Section;
import atdd.station.domain.Station;
import io.jsonwebtoken.lang.Collections;

public class LinePathSearch {

    private WeightedMultigraph<Station, DefaultWeightedEdge> graph;
    private DijkstraShortestPath<Station, DefaultWeightedEdge> dijkstra;

    public LinePathSearch(List<Section> sections) {
        validate(sections);
        settingGraph(sections);
        this.dijkstra = new DijkstraShortestPath<>(graph);
    }

    private void validate(List<Section> sections) {
        if (Collections.isEmpty(sections)) {
            throw new InputException();
        }
    }

    private void settingGraph(List<Section> sections) {
        for (Section section : sections) {
            Station upStation = section.getUpstation();
            Station downStation = section.getDownStatoin();
            graph.addVertex(upStation);
            graph.addVertex(downStation);
            graph.setEdgeWeight(graph.addEdge(upStation, downStation), section.getDistance());
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
