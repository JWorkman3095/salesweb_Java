package com.maxtrain.bootcamp.sales.order;

import javax.persistence.*;

import com.maxtrain.bootcamp.sales.customer.Customer;

@Entity
public class Orders {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(columnDefinition="int")
	private int id;
	@Column(length=50, nullable=false)
	private String description;
	@Column(columnDefinition="decimal(9,2) NOT NULL")
	private double total;
	//custom like for Capstone
	@Column(length=30, nullable=false)
	private String status;
	
	// ManyToOne many orders for one customer, optional to false means can not be null
	// establishes FK
	@ManyToOne(optional=false)
	//puts it in the table column 
	@JoinColumn(name="customerId", columnDefinition="int")
	private Customer customer;
	
	//default constructor
	public Orders() {}
		

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}		

}
