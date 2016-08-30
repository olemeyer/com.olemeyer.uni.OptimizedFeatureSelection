package com.olemeyer.uni.optfeatureselection.controller;

import com.olemeyer.uni.optfeatureselection.featuremodel.FeatureModel;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Ole Meyer
 */
public class Request {
    @Min(1)
    @Max(5000)
    private int populationSize;
    @Min(0)
    @Max(1)
    private double rgMutationProbability;
    @Min(0)
    @Max(1)
    private double sgMutationProbability;
    @Min(0)
    @Max(1)
    private double reproductionQuota;
    @Min(0)
    @Max(100000)
    private int maxIterations;
    @Min(0)
    private int minAttractorLength;

    @Valid
    private FeatureModel featureModel;

    public double getRgMutationProbability() {
        return rgMutationProbability;
    }

    public void setRgMutationProbability(double rgMutationProbability) {
        this.rgMutationProbability = rgMutationProbability;
    }

    public double getSgMutationProbability() {
        return sgMutationProbability;
    }

    public void setSgMutationProbability(double sgMutationProbability) {
        this.sgMutationProbability = sgMutationProbability;
    }

    public double getReproductionQuota() {
        return reproductionQuota;
    }

    public void setReproductionQuota(double reproductionQuota) {
        this.reproductionQuota = reproductionQuota;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public int getMinAttractorLength() {
        return minAttractorLength;
    }

    public void setMinAttractorLength(int minAttractorLength) {
        this.minAttractorLength = minAttractorLength;
    }

    public FeatureModel getFeatureModel() {
        return featureModel;
    }

    public void setFeatureModel(FeatureModel featureModel) {
        this.featureModel = featureModel;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }
}
