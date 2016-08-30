package com.olemeyer.uni.optfeatureselection.featuremodel;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Ole Meyer
 */
public class Feature extends Identifieable {

    @NotNull
    @Valid
    private FeatureGroup[] featureGroups = new FeatureGroup[0];

    private boolean active;

    private transient FeatureGroup parentFeatureGroup;

    public FeatureGroup[] getFeatureGroups() {
        return featureGroups;
    }

    public void setFeatureGroups(FeatureGroup[] featureGroups) {
        this.featureGroups = featureGroups;
    }

    public FeatureGroup getParentFeatureGroup() {
        return parentFeatureGroup;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
