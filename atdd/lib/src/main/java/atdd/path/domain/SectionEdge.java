package atdd.path.domain;

import org.jgrapht.graph.DefaultWeightedEdge;

import atdd.section.domain.Section;

public class SectionEdge extends DefaultWeightedEdge {

    private final Section section;

    private SectionEdge(Section section) {
        this.section = section;
    }

    public static SectionEdge of(Section section) {
        return new SectionEdge(section);
    }

    public int getExtraFee() {
        return section.getExtraFee();
    }

}
