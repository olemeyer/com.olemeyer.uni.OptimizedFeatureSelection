package com.olemeyer.uni.optfeatureselection.controller;

import java.util.List;

/**
 * @author Ole Meyer
 */
public class Response {
    private List<String> configuration;
    private double fitnessScore;
    private long executionTime;
    private boolean attractorReached;
    private int iterations;
    private double[] evolution;

    public Response(List<String> configuration, double fitnessScore, long executionTime, boolean attractorReached, int iterations, double[] evolution) {
        this.configuration = configuration;
        this.fitnessScore = fitnessScore;
        this.executionTime = executionTime;
        this.attractorReached = attractorReached;
        this.iterations = iterations;
        this.evolution = evolution;
    }

    public boolean isAttractorReached() {
        return attractorReached;
    }

    public void setAttractorReached(boolean attractorReached) {
        this.attractorReached = attractorReached;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public double[] getEvolution() {
        return evolution;
    }

    public void setEvolution(double[] evolution) {
        this.evolution = evolution;
    }

    public List<String> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(List<String> configuration) {
        this.configuration = configuration;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public double getFitnessScore() {
        return fitnessScore;
    }

    public void setFitnessScore(double fitnessScore) {
        this.fitnessScore = fitnessScore;
    }
}
