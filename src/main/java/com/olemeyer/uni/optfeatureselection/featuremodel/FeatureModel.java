package com.olemeyer.uni.optfeatureselection.featuremodel;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ole Meyer
 */
public class FeatureModel extends Identifieable {
    @Valid
    @NotNull
    private Feature rootFeature;

    @Valid
    @NotNull
    private List<Constraint> excludes = new LinkedList<>();

    @Valid
    @NotNull
    private List<Constraint> requires = new LinkedList<>();

    @Valid
    @NotNull
    private List<Softgoal> softgoals = new LinkedList<>();

    public Feature getRootFeature() {
        return rootFeature;
    }

    public void setRootFeature(Feature rootFeature) {
        this.rootFeature = rootFeature;
    }

    public List<Constraint> getExcludes() {
        return excludes;
    }

    public void setExcludes(List<Constraint> excludes) {
        this.excludes = excludes;
    }

    public List<Constraint> getRequires() {
        return requires;
    }

    public void setRequires(List<Constraint> requires) {
        this.requires = requires;
    }

    public List<Softgoal> getSoftgoals() {
        return softgoals;
    }

    public void setSoftgoals(List<Softgoal> softgoals) {
        this.softgoals = softgoals;
    }


}
