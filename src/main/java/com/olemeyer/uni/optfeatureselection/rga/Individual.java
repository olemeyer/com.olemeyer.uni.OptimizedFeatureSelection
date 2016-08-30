package com.olemeyer.uni.optfeatureselection.rga;

import com.olemeyer.uni.optfeatureselection.featuremodel.*;
import com.olemeyer.uni.optfeatureselection.rga.mutation.AlreadyMutatedException;

import java.util.*;

/**
 * @author Ole Meyer
 */
public class Individual {

    public static Random random = new Random();


    private List<StructuralGene> structuralGenes = new LinkedList<>();
    private List<StructuralGene> mutableStructuralGenes = new LinkedList<>();
    private List<StructuralGene> mutableLeafStructuralGenes = new LinkedList<>();
    private Map<String, StructuralGene> idToGene = new HashMap<>();
    private List<RegelatoryGene> regelatoryGenes = new LinkedList<>();
    private FeatureModel featureModel;
    private double fitnessScore = 0;
    private double totalFitnessScore = 0;

    public Individual(FeatureModel featureModel) {
        this.featureModel = featureModel;
        buildStructuralGenes(featureModel.getRootFeature(), featureModel.getRequires(), featureModel.getExcludes());
        buildRegulatoryGenes(featureModel.getSoftgoals());
    }

    public Individual(FeatureModel featureModel, List<String> configuration) {
        this.featureModel = featureModel;
        buildStructuralGenes(featureModel.getRootFeature(), featureModel.getRequires(), featureModel.getExcludes());
        buildRegulatoryGenes(featureModel.getSoftgoals());
        for (StructuralGene structuralGene : structuralGenes) {
            structuralGene.setActive(false);
        }
        for (String id : configuration) {
            idToGene.get(id).setActive(true);
        }
    }


    private void buildRegulatoryGenes(List<Softgoal> softgoals) {
        for (Softgoal softgoal : softgoals) {
            List<Connection> list = new LinkedList<>();
            for (Influence influence : softgoal.getInfluences()) {
                list.add(new Connection(idToGene.get(influence.getFeatureId()), influence.getWeight()));
            }
            regelatoryGenes.add(new RegelatoryGene(softgoal, list));
        }
    }

    private void buildStructuralGenes(Feature rootFeature, List<Constraint> requires, List<Constraint> excludes) {
        for (FeatureGroup fg : rootFeature.getFeatureGroups()) {
            for (Feature feature : fg.getFeatures()) {
                StructuralGene gene = new StructuralGene(feature, fg, rootFeature, requires, excludes, feature.isActive());
                structuralGenes.add(gene);
                idToGene.put(gene.getId(), gene);
                if (gene.mutable()) mutableStructuralGenes.add(gene);
                if (gene.mutable() && gene.isLeaf()) mutableLeafStructuralGenes.add(gene);
                buildStructuralGenes(feature, requires, excludes);
            }
        }
    }

    public void mutate() {
        int gene = random.nextInt(mutableStructuralGenes.size() - 1);
        List<String> mutatesGenes = new LinkedList<>();
        try {
            StructuralGene g = mutableStructuralGenes.get(gene);
            //g=idToGene.get("1.1");
            g.setActive(!g.isActive(), idToGene, mutatesGenes);
        } catch (AlreadyMutatedException e) {
            e.printStackTrace();
        }
    }


    public List<String> getConfiguration() {
        List<String> list = new LinkedList<>();
        for (StructuralGene gene : structuralGenes) {
            if (gene.isActive()) list.add(gene.getId());
        }
        return list;
    }

    public double getFitnessScore() {

        return fitnessScore;
    }

    public double getTotalFitnessScore() {
        return totalFitnessScore;
    }

    public void setTotalFitnessScore(double totalFitnessScore) {
        this.totalFitnessScore = totalFitnessScore;
    }

    public void calcFitnessScore() {
        double score = 0;
        for (RegelatoryGene rg : regelatoryGenes) {
            if (rg.isActive()) {
                for (Connection connection : rg.getConnectedStructuralGenes()) {
                    if (connection.getStructuralGene().isActive()) {
                        score += rg.getSoftgoal().getWeight() * connection.getWeight();
                    }
                }
            }
        }
        this.fitnessScore = score;

        score = 0;
        for (RegelatoryGene rg : regelatoryGenes) {
            for (Connection connection : rg.getConnectedStructuralGenes()) {
                if (connection.getStructuralGene().isActive()) {
                    score += rg.getSoftgoal().getWeight() * connection.getWeight();
                }
            }
        }
        this.totalFitnessScore = score;
    }

    public List<StructuralGene> getStructuralGenes() {
        return structuralGenes;
    }

    public void setStructuralGenes(List<StructuralGene> structuralGenes) {
        this.structuralGenes = structuralGenes;
    }

    public List<StructuralGene> getMutableStructuralGenes() {
        return mutableStructuralGenes;
    }

    public void setMutableStructuralGenes(List<StructuralGene> mutableStructuralGenes) {
        this.mutableStructuralGenes = mutableStructuralGenes;
    }

    public Map<String, StructuralGene> getIdToGene() {
        return idToGene;
    }

    public void setIdToGene(Map<String, StructuralGene> idToGene) {
        this.idToGene = idToGene;
    }

    public List<RegelatoryGene> getRegelatoryGenes() {
        return regelatoryGenes;
    }

    public void setRegelatoryGenes(List<RegelatoryGene> regelatoryGenes) {
        this.regelatoryGenes = regelatoryGenes;
    }

    public FeatureModel getFeatureModel() {
        return featureModel;
    }

    public void setFeatureModel(FeatureModel featureModel) {
        this.featureModel = featureModel;
    }

    public List<StructuralGene> getMutableLeafStructuralGenes() {
        return mutableLeafStructuralGenes;
    }

    public void setMutableLeafStructuralGenes(List<StructuralGene> mutableLeafStructuralGenes) {
        this.mutableLeafStructuralGenes = mutableLeafStructuralGenes;
    }

    public Individual recombine(Individual parent2) throws AlreadyMutatedException {
        Individual child = new Individual(featureModel, getConfiguration());
        //Crossover
        int half = parent2.getMutableLeafStructuralGenes().size() / 2;
        for (int i = 0; i < half; ++i) {
            StructuralGene pgene = parent2.getMutableLeafStructuralGenes().get(i);
            child.idToGene.get(pgene.getId()).setActive(pgene.isActive(), idToGene, new LinkedList<String>());
        }
        return child;
    }
}
