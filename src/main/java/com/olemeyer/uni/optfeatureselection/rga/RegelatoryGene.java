package com.olemeyer.uni.optfeatureselection.rga;

import com.olemeyer.uni.optfeatureselection.featuremodel.Softgoal;

import java.util.List;

/**
 * @author Ole Meyer
 */
public class RegelatoryGene {

    public static Softgoal active;

    private Softgoal softgoal;
    private List<Connection> connectedStructuralGenes;

    public RegelatoryGene(Softgoal softgoal, List<Connection> connectedStructuralGenes) {
        this.softgoal = softgoal;
        this.connectedStructuralGenes = connectedStructuralGenes;
    }

    public static Softgoal getActive() {
        return active;
    }

    public static void setActive(Softgoal active) {
        RegelatoryGene.active = active;
    }

    public Softgoal getSoftgoal() {
        return softgoal;
    }

    public void setSoftgoal(Softgoal softgoal) {
        this.softgoal = softgoal;
    }

    public List<Connection> getConnectedStructuralGenes() {
        return connectedStructuralGenes;
    }

    public void setConnectedStructuralGenes(List<Connection> connectedStructuralGenes) {
        this.connectedStructuralGenes = connectedStructuralGenes;
    }

    public boolean isActive() {
        return softgoal.equals(active);
    }
}
