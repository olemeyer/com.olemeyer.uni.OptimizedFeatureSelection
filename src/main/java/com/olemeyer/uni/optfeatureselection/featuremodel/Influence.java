package com.olemeyer.uni.optfeatureselection.featuremodel;

import javax.validation.constraints.NotNull;

/**
 * @author Ole Meyer
 */
public class Influence {
    @NotNull
    private String featureId;
    @NotNull
    private double weight;

    public String getFeatureId() {
        return featureId;
    }

    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
