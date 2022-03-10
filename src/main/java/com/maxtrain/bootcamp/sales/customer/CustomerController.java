package com.maxtrain.bootcamp.sales.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
//sending and receiving json
@RestController
@RequestMapping("/api/customers")

public class CustomerController {
	
	@Autowired
	//variable for repo
	private CustomerRepository custRepo;
	
	//reads all data from customer
	//like http get/ put in C#
	@GetMapping
	//brings back an array of cus instances
	public ResponseEntity<Iterable<Customer>> GetCustomers() {
		var customers = custRepo.findAll();
		return new ResponseEntity<Iterable<Customer>>(customers, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	//by PK
	public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
		var customer = custRepo.findById(id);
		if(customer.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);
	}
	
	// #5 read by code similar to login in Capstone built like PK
	@GetMapping ("code/{code}")
	public ResponseEntity<Customer> getCustomerByCode(@PathVariable String code) {
		var cust = custRepo.findByCode(code);
		if(cust.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		//must call get to retrieve the cust at var above
		return new ResponseEntity<Customer>(cust.get(), HttpStatus.OK);
	}
	
	//add post
	@PostMapping
	public ResponseEntity<Customer> postCustomer(@RequestBody Customer customer) {
		if (customer == null || customer.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var cust = custRepo.save(customer);
		return new ResponseEntity<Customer>(cust, HttpStatus.CREATED);
	}
	//add put
	//SuppressWarnings allowed to use a generic type without returning for putCustomer
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putCustomer(@PathVariable int id, @RequestBody Customer customer){
		if (customer == null || customer.getId() == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// this makes sure we have an instance of customer
		var cust = custRepo.findById(customer.getId());
		if(cust.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}
		custRepo.save(customer);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	//delete
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteCustomer(@PathVariable int id) {
		var customer = custRepo.findById(id);
		if (customer.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		custRepo.delete(customer.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);		
		
	}
}
