package com.olemeyer.uni.optfeatureselection.rga.mutation.expression;

import com.olemeyer.uni.optfeatureselection.rga.StructuralGene;
import com.olemeyer.uni.optfeatureselection.rga.mutation.AlreadyMutatedException;

import java.util.List;
import java.util.Map;

/**
 * @author Ole Meyer
 */
public interface Expression {
    void activate(Map<String, StructuralGene> idToFeature, List<String> mutatedGenes, StructuralGene caller) throws AlreadyMutatedException;

    void deactivate(Map<String, StructuralGene> idToGene, List<String> mutatedGenes, StructuralGene caller) throws AlreadyMutatedException;
}
