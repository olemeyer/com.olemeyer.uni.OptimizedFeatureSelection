package com.olemeyer.uni.optfeatureselection.rga.mutation.expression;

import com.olemeyer.uni.optfeatureselection.rga.StructuralGene;
import com.olemeyer.uni.optfeatureselection.rga.mutation.AlreadyMutatedException;

import java.util.List;
import java.util.Map;

/**
 * @author Ole Meyer
 */
public class SimpleExpression implements Expression {

    private String id;

    public SimpleExpression(String id) {
        this.id = id;
    }


    @Override
    public void activate(Map<String, StructuralGene> idToGene, List<String> mutatedGenes, StructuralGene caller) throws AlreadyMutatedException {
        StructuralGene gene = idToGene.get(id);
        if (gene == null) return;
        gene.setActive(true, idToGene, mutatedGenes);
    }


    @Override
    public void deactivate(Map<String, StructuralGene> idToGene, List<String> mutatedGenes, StructuralGene caller) throws AlreadyMutatedException {
        StructuralGene gene = idToGene.get(id);
        if (gene == null) return;
        gene.setActive(false, idToGene, mutatedGenes);
    }
}
