package atdd.section.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import atdd.line.domain.Line;
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

    public Section(Line line, Station upStation, Station downStation, int distance) {
        this.line = line;
        this.upstation = upStation;
        this.downStatoin = downStation;
        this.distance = distance;
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
