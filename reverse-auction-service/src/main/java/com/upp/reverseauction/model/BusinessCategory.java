package com.upp.reverseauction.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class BusinessCategory implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public BusinessCategory() {
        super();
    }

    public BusinessCategory(String name) {
        super();
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getNameLowercase() {
        return this.name.toLowerCase();
    }

    public void setName(String name) {
        this.name = name;
    }
}
