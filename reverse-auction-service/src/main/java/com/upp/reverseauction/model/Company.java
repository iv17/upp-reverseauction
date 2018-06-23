package com.upp.reverseauction.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Company implements Serializable {
	
	@Id
	@GeneratedValue
	private Long id;

	@OneToOne(mappedBy = "company")
	private PrivateUser agent;

	@ManyToMany
	@JoinTable(name="company_business_category", joinColumns = @JoinColumn(name="company_id"),
        inverseJoinColumns = @JoinColumn(name="business_category_id"))
    @JsonIgnore
	private List<BusinessCategory> businessCategory;
	
	private double distance;

	public Company() {
		super();
	}

	public Company(PrivateUser agent, List<BusinessCategory> businessCategory, double distance) {
		super();
		this.agent = agent;
		this.businessCategory = businessCategory;
		this.distance = distance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PrivateUser getAgent() {
		return agent;
	}

	public void setAgent(PrivateUser agent) {
		this.agent = agent;
	}

	public List<BusinessCategory> getBusinessCategory() {
		return businessCategory;
	}

	public void setBusinessCategory(List<BusinessCategory> businessCategory) {
		this.businessCategory = businessCategory;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
}
