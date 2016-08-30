package com.olemeyer.uni.optfeatureselection.featuremodel;

import javax.validation.constraints.NotNull;

/**
 * @author Ole Meyer
 */
public class Constraint {
    @NotNull
    private String fromId;

    @NotNull
    private String toId;

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }


}
