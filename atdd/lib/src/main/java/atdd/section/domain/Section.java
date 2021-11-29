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

    public boolean isMatchedStation(Section compare) {
        return upstation.equals(compare.upstation) || upstation.equals(compare.getDownStatoin()) || downStatoin.equals(compare.downStatoin) || downStatoin.equals(compare.getUpstation());
    }

    public boolean isSameUpstation(Section compare) {
        return upstation.equals(compare.upstation);
    }

    public boolean isSameDownstation(Section compare) {
        return downStatoin.equals(compare.downStatoin);
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
