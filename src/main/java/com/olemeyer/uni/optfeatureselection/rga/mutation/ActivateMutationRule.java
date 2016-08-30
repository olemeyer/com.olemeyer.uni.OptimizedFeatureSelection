package com.olemeyer.uni.optfeatureselection.rga.mutation;

import com.olemeyer.uni.optfeatureselection.rga.StructuralGene;
import com.olemeyer.uni.optfeatureselection.rga.mutation.expression.Expression;

import java.util.List;
import java.util.Map;

/**
 * @author Ole Meyer
 */
public class ActivateMutationRule implements MutationRule {
    private Expression expression;

    public ActivateMutationRule(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void apply(Map<String, StructuralGene> idToGene, List<String> mutatedGenes, StructuralGene caller) throws AlreadyMutatedException {
        expression.activate(idToGene, mutatedGenes, caller);
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}
