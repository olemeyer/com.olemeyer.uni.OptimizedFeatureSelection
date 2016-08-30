package com.olemeyer.uni.optfeatureselection.featuremodel;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Ole Meyer
 */
public abstract class Identifieable {

    @NotEmpty
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
