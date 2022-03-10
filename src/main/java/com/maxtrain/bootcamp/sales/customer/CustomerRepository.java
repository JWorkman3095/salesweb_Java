package com.maxtrain.bootcamp.sales.customer;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	//returning a customer code that is unqie
	//<Customer>findByStatus(String Code); Optional
	Optional<Customer> findByCode(String code);
}
