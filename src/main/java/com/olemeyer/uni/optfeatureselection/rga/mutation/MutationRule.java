package com.olemeyer.uni.optfeatureselection.rga.mutation;

import com.olemeyer.uni.optfeatureselection.rga.StructuralGene;

import java.util.List;
import java.util.Map;

/**
 * @author Ole Meyer
 */
public interface MutationRule {
    void apply(Map<String, StructuralGene> idToGene, List<String> mutatedGenes, StructuralGene caller) throws AlreadyMutatedException;
}
