package com.ttn.ecommerceApplication.ecommerceApplication.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Token {
    @Id
    @GeneratedValue
    Long id;
    String name;
    String randomToken;
    Long timeInMill;
    boolean isExpired;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRandomToken() {
        return randomToken;
    }

    public void setRandomToken(String randomToken) {
        this.randomToken = randomToken;
    }

    public Long getTimeInMill() {
        return timeInMill;
    }

    public void setTimeInMill(Long timeInMill) {
        this.timeInMill = timeInMill;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }
}
