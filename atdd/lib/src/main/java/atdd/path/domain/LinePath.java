package atdd.path.domain;

import java.util.Collections;
import java.util.List;

import org.jgrapht.GraphPath;

import atdd.common.InputException;
import atdd.member.domain.Member;
import atdd.path.constant.DiscountRange;
import atdd.path.constant.DistanceFee;
import atdd.station.domain.Station;

public class LinePath {
    private List<Station> stations;
    private List<SectionEdge> edges;
    private int minDistance;
    private int extraFee;

    public LinePath(GraphPath<Station, SectionEdge> path) {
        validate(path);
        this.stations = path.getVertexList();
        this.edges = path.getEdgeList();
        this.minDistance = (int)Math.round(path.getWeight());
        this.extraFee = getExtraFee();
    }

    private int getExtraFee() {
        return edges.stream()
            .min((one, two) -> Integer.compare(two.getExtraFee(), one.getExtraFee()))
            .map(SectionEdge::getExtraFee)
            .orElse(0);
    }

    private void validate(GraphPath<Station, SectionEdge> path) {
        if (path == null) {
            throw new InputException();
        }
    }

    public static LinePath of(GraphPath<Station, SectionEdge> path) {
        return new LinePath(path);
    }

    public List<Station> getStations() {
        return Collections.unmodifiableList(stations);
    }

    public int getMinDistance() {
        return minDistance;
    }

    public double getFare(Member loginMember) {
        DiscountRange discountRange = DiscountRange.findByMember(loginMember);
        int distanceFee = DistanceFee.findByDistance(minDistance);
        return (distanceFee + extraFee - discountRange.getStatndard()) * discountRange.getDiscountRate();
    }

}
