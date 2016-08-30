package com.olemeyer.uni.optfeatureselection.rga;

/**
 * @author Ole Meyer
 */
public class Connection {
    private StructuralGene structuralGene;
    private double weight;

    public Connection(StructuralGene structuralGene, double weight) {
        this.structuralGene = structuralGene;
        this.weight = weight;
    }

    public StructuralGene getStructuralGene() {
        return structuralGene;
    }

    public void setStructuralGene(StructuralGene structuralGene) {
        this.structuralGene = structuralGene;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
