package com.olemeyer.uni.optfeatureselection.rga;

import com.olemeyer.uni.optfeatureselection.featuremodel.Constraint;
import com.olemeyer.uni.optfeatureselection.featuremodel.Feature;
import com.olemeyer.uni.optfeatureselection.featuremodel.FeatureGroup;
import com.olemeyer.uni.optfeatureselection.rga.mutation.ActivateMutationRule;
import com.olemeyer.uni.optfeatureselection.rga.mutation.AlreadyMutatedException;
import com.olemeyer.uni.optfeatureselection.rga.mutation.DeactivateMutationRule;
import com.olemeyer.uni.optfeatureselection.rga.mutation.MutationRule;
import com.olemeyer.uni.optfeatureselection.rga.mutation.expression.MinExpression;
import com.olemeyer.uni.optfeatureselection.rga.mutation.expression.SimpleExpression;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Ole Meyer
 */
public class StructuralGene {
    private String id;
    private List<MutationRule> activateRules = new LinkedList<>();
    private List<MutationRule> deactivateRules = new LinkedList<>();
    private boolean active;
    private boolean mutable = true;
    private boolean leaf = false;

    private String parentId;

    public StructuralGene(Feature feature, FeatureGroup featureGroup, Feature parent, List<Constraint> requires, List<Constraint> excludes, boolean active) {

        this.id = feature.getId();
        this.active = active;
        this.parentId = parent.getId();
        leaf = feature.getFeatureGroups().length == 0;

        if (featureGroup.getMin() == featureGroup.getFeatures().length) {
            this.mutable = false;
        }

        //#Activation

        //Parent
        activateRules.add(new ActivateMutationRule(new SimpleExpression(parent.getId())));

        //Constraints
        for (Constraint req : requires) {
            if (req.getFromId().equals(id)) {
                activateRules.add(new ActivateMutationRule(new SimpleExpression(req.getToId())));
            }
        }

        for (Constraint ex : excludes) {
            if (ex.getFromId().equals(id)) {
                activateRules.add(new DeactivateMutationRule(new SimpleExpression(ex.getToId())));
            } else if (ex.getToId().equals(id)) {
                activateRules.add(new DeactivateMutationRule(new SimpleExpression(ex.getFromId())));
            }
        }

        //Alternative Groups
        if (featureGroup.getMax() < featureGroup.getFeatures().length) {
            activateRules.add(new DeactivateMutationRule(new MinExpression(featureGroup.getFeatures().length - featureGroup.getMax(), featureGroup.getIdList(), parent.getId())));

            //activateRules.add(new ActivateMutationRule(new MaxExpression(featureGroup.getMax(),featureGroup.getIdList())));
        }

        //Childs
        for (FeatureGroup fg : feature.getFeatureGroups()) {
            if (fg.getMin() > 0) {
                activateRules.add(new ActivateMutationRule(new MinExpression(fg.getMin(), fg.getIdList(), id)));
            }
            if (fg.getMax() < fg.getFeatures().length) {
                activateRules.add(new DeactivateMutationRule(new MinExpression(fg.getFeatures().length - fg.getMax(), fg.getIdList(), id)));
            }
        }

        //#Deactivation

        //Childs
        for (FeatureGroup fg : feature.getFeatureGroups()) {
            for (Feature f : fg.getFeatures()) {
                deactivateRules.add(new DeactivateMutationRule(new SimpleExpression(f.getId())));
            }
        }

        //Constraints
        for (Constraint req : requires) {
            if (req.getToId().equals(id)) {
                deactivateRules.add(new DeactivateMutationRule(new SimpleExpression(req.getFromId())));
            }
        }


        //Alternative Groups
        if (featureGroup.getMin() > 0) {
            deactivateRules.add(new ActivateMutationRule(new MinExpression(featureGroup.getMin(), featureGroup.getIdList(), parent.getId())));
            //deactivateRules.add(new DeactivateMutationRule(new MaxExpression(featureGroup.getMin(),featureGroup.getIdList())));

        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MutationRule> getActivateRules() {
        return activateRules;
    }

    public void setActivateRules(List<MutationRule> activateRules) {
        this.activateRules = activateRules;
    }

    public List<MutationRule> getDeactivateRules() {
        return deactivateRules;
    }

    public void setDeactivateRules(List<MutationRule> deactivateRules) {
        this.deactivateRules = deactivateRules;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean mutable() {
        return mutable;
    }

    public boolean isMutable() {
        return mutable;
    }

    public void setMutable(boolean mutable) {
        this.mutable = mutable;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public void setActive(boolean active, Map<String, StructuralGene> idToGene, List<String> mutatedGenes) throws AlreadyMutatedException {


        if (active == this.active) return;
        if (mutatedGenes.contains(id + ":" + String.valueOf(active))) return;

        if (mutatedGenes.contains(parentId + ":" + String.valueOf(false)) && active) {
            System.out.println("Aktivierung verworfen wegen deaktivierten Eltern: Gen " + id);
            return;
        }

        if (active) {
            //System.out.println("Aktiviere Gen "+id);
        } else {
            //System.out.println("Deaktiviere Gen "+id);
        }


        if (!(mutatedGenes.contains(parentId + ":" + String.valueOf(false)) && !active)) {
            if (mutatedGenes.contains(id + ":" + String.valueOf(!active))) throw new AlreadyMutatedException();
        }


        mutatedGenes.add(id + ":" + String.valueOf(active));


        if (active) {
            for (MutationRule rule : activateRules) {
                rule.apply(idToGene, mutatedGenes, this);
            }
        } else {
            for (MutationRule rule : deactivateRules) {
                rule.apply(idToGene, mutatedGenes, this);
            }
        }

        this.active = active;
    }


}
