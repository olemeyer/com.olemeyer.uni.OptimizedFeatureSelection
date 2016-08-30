package com.olemeyer.uni.optfeatureselection.featuremodel;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Ole Meyer
 */
public class Softgoal {
    @NotNull
    private String id;
    @NotNull
    private double weight;

    @NotNull
    @Valid
    private List<Influence> influences;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Influence> getInfluences() {
        return influences;
    }

    public void setInfluences(List<Influence> influences) {
        this.influences = influences;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
