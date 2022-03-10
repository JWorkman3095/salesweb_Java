package com.maxtrain.bootcamp.sales.order;

import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Orders, Integer> {
     // for item in review like adding a method in Link
	// creating this findByStatus to do this enables us to put it in a column
	Iterable<Orders> findByStatus(String status);
}
