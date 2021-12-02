package atdd.path.domain;

import java.util.Collections;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import atdd.member.domain.Member;
import atdd.station.domain.Station;

public class LinePath {
    private List<Station> stations;
    private List<DefaultWeightedEdge> edges;

    public LinePath(GraphPath<Station, DefaultWeightedEdge> path) {
        this.stations = path.getVertexList();
        this.edges = path.getEdgeList();
    }

    public static LinePath of(GraphPath<Station, DefaultWeightedEdge> path) {
        return new LinePath(path);
    }

    public List<Station> getStations() {
        return Collections.unmodifiableList(stations);
    }

    public int getMinDistance() {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getFare(Member loginMember) {
        // TODO Auto-generated method stub
        return 0;
    }

}
