package atdd.section.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import atdd.line.domain.Line;
import atdd.section.exception.DistanceLongException;
import atdd.station.domain.Station;

@Entity
public class Section {
    public static final Section EMPTY = new Section();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "up_station_id")
    private Station upstation;

    @ManyToOne
    @JoinColumn(name = "down_station_id")
    private Station downStatoin;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;

    private int distance;

    public Section() {}

    public Section(Long id, Line line, Station upStation, Station downStation, int distance) {
        this.id = id;
        this.line = line;
        this.upstation = upStation;
        this.downStatoin = downStation;
        this.distance = distance;
    }

    public Section(Line line, Station upStation, Station downStation, int distance) {
        this(null, line, upStation, downStation, distance);
    }

    public Long getId() {
        return id;
    }

    public int getDistance() {
        return distance;
    }

    public Station getUpstation() {
        return upstation;
    }

    public Station getDownStatoin() {
        return downStatoin;
    }

    public boolean isBefore(Section compare) {
        return downStatoin.equals(compare.upstation);
    }

    public boolean isSameSection(Section compare) {
        return upstation.equals(compare.upstation) && downStatoin.equals(compare.downStatoin);
    }

    public boolean isSameUpstation(Section compare) {
        return upstation.equals(compare.upstation);
    }

    public boolean isMatchedUpStation(Section compare) {
        return upstation.equals(compare.upstation) || upstation.equals(compare.downStatoin);
    }

    public boolean isMatchedDownStation(Section compare) {
        return downStatoin.equals(compare.upstation) || downStatoin.equals(compare.downStatoin);
    }

    public boolean isSameUpstation(Station station) {
        return upstation.equals(station);
    }

    public boolean isSameDownstation(Section compare) {
        return downStatoin.equals(compare.downStatoin);
    }

    public boolean isSameDownstation(Station station) {
        return downStatoin.equals(station);
    }

    public void updateUpstation(Station station) {
        this.upstation = station;
    }

    public void updateDownstation(Station station) {
        this.downStatoin = station;
    }

    public void updateDistance(int distance) {
        if (distance <= 0) {
            throw new DistanceLongException();
        }
        this.distance = distance;
    }

    public static boolean isEmpty(Section section) {
        return Objects.isNull(section) || Objects.isNull(section.getId());
    }

    public int getExtraFee() {
        return line.getExtraFee();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Section other = (Section)obj;
        return Objects.equals(id, other.id);
    }

}
