package com.olemeyer.uni.optfeatureselection.featuremodel;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ole Meyer
 */
public class FeatureGroup {
    @Min(0)
    private int min;
    @Min(0)
    private int max;
    @Valid
    @NotNull
    private Feature[] features;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Feature[] getFeatures() {
        return features;
    }

    public void setFeatures(Feature[] features) {
        this.features = features;
    }

    public List<String> getIdList() {
        List<String> ids = new LinkedList<>();
        for (Feature feature : features) ids.add(feature.getId());
        return ids;
    }


}
