package com.olemeyer.uni.optfeatureselection.rga.mutation.expression;

import com.olemeyer.uni.optfeatureselection.rga.Individual;
import com.olemeyer.uni.optfeatureselection.rga.StructuralGene;
import com.olemeyer.uni.optfeatureselection.rga.mutation.AlreadyMutatedException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Ole Meyer
 */
public class MinExpression implements Expression {

    private int min;
    private List<String> ids = new LinkedList<>();
    private List<StructuralGene> genes;
    private StructuralGene parent;
    private String parentId;

    public MinExpression(int min, List<String> ids, String parentId) {
        this.min = min;
        this.ids = ids;
        this.parentId = parentId;
    }

    //Aktiviere mindestens n Kinder
    @Override
    public void activate(Map<String, StructuralGene> idToGene, List<String> mutatedGenes, StructuralGene caller) throws AlreadyMutatedException {
        if (genes == null) loadGenes(idToGene);
        if (parent == null) parent = idToGene.get(parentId);

        if (parent != null && !checkActive(parent, mutatedGenes)) return;

        int count = 0;
        List<StructuralGene> possibleGenes = new LinkedList<>();
        for (StructuralGene gene : genes) {
            if (gene.equals(caller)) {
                //Do nothing
            } else if (checkActive(gene, mutatedGenes)) {
                ++count;
            } else {
                possibleGenes.add(gene);
            }
        }

        if (count >= min) return;


        for (int i = count; i < min; ++i) {
            StructuralGene g = possibleGenes.get(Individual.random.nextInt(possibleGenes.size()));
            g.setActive(true, idToGene, mutatedGenes);
            possibleGenes.remove(g);
        }

        //TODO Was ist wenn zu wenig Optionen gefunden?
    }

    @Override
    public void deactivate(Map<String, StructuralGene> idToGene, List<String> mutatedGenes, StructuralGene caller) throws AlreadyMutatedException {

        if (genes == null) loadGenes(idToGene);
        if (parent == null) parent = idToGene.get(parentId);


        if (parent != null && !checkActive(parent, mutatedGenes)) return;


        int count = 0;
        List<StructuralGene> possibleGenes = new LinkedList<>();
        for (StructuralGene gene : genes) {
            if (gene.equals(caller)) {
                //Do nothing
            } else if (!checkActive(gene, mutatedGenes)) {
                ++count;
            } else {
                possibleGenes.add(gene);
            }
        }

        if (count >= min) return;


        for (int i = count; i < min; ++i) {
            StructuralGene g = possibleGenes.get(Individual.random.nextInt(possibleGenes.size()));
            g.setActive(false, idToGene, mutatedGenes);
            possibleGenes.remove(g);
        }

        //TODO Was ist wenn zu wenig Optionen gefunden?
    }

    private boolean checkActive(StructuralGene gene, List<String> mutatedGenes) {
        return (gene.isActive() && !mutatedGenes.contains(gene.getId() + ":" + String.valueOf(false)))
                || mutatedGenes.contains(gene.getId() + ":" + String.valueOf(true));
    }

    private void loadGenes(Map<String, StructuralGene> idToGene) {
        genes = new LinkedList<>();
        for (String id : ids) {
            genes.add(idToGene.get(id));
        }
    }
}
