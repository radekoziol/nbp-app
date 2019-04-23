package com.app.model.user;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
public class FacebookUser extends User {

    private String service;
    private long externalId;

    public FacebookUser() {
    }

    public FacebookUser(String service, long externalId) {
        this.service = service;
        this.externalId = externalId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public long getExternalId() {
        return externalId;
    }

    public void setExternalId(long externalId) {
        this.externalId = externalId;
    }
}
