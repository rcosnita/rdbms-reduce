package com.rcosnita.experiments.rdbmsreduce.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity used to model a provisioning item element.
 * 
 * @author Radu Viorel Cosnita
 * @since 10.07.2012
 */

@Table(name="items")
public class ProvisioningItem implements Serializable {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="account_id")
	private Integer accountId;
	
	@Column(name="prov_id")
	private Integer provId;
	
	@Column(name="prov_type")
	private Integer provType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getProvId() {
		return provId;
	}

	public void setProvId(Integer provId) {
		this.provId = provId;
	}
}