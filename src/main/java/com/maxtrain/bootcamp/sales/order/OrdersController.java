package com.maxtrain.bootcamp.sales.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
//sending and receiving json
@RestController
@RequestMapping("/api/orders")
public class OrdersController {	
	@Autowired
	//variable for repo
	private OrdersRepository ordRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Orders>> getOrders() {
		var orders = ordRepo.findAll();
		return new ResponseEntity<Iterable<Orders>>(orders, HttpStatus.OK);
	}
	
	// by PK
	@GetMapping("{id}")
	public ResponseEntity<Orders> getOrder(@PathVariable int id) {
		var Orders = ordRepo.findById(id);
		if(Orders.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Orders>(Orders.get(), HttpStatus.OK);
	}
	
	// #4 (in notes) a collection of orders in review status
	@GetMapping ("reviews")
	public ResponseEntity<Iterable<Orders>> getOrdersInReview() {
		var orders = ordRepo.findByStatus("REVIEW");
		return new ResponseEntity<Iterable<Orders>>(orders, HttpStatus.OK);
	}
	
	//add post
	@PostMapping
	public ResponseEntity<Orders> postOrder(@RequestBody Orders order) {
		if (order == null || order.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		//new order has status of NEW (before Save)
		order.setStatus("NEW");
		var ord = ordRepo.save(order);
		return new ResponseEntity<Orders>(ord, HttpStatus.CREATED);
	}
	
	// if you put<> with nothing in it you will get a warming see below
	@SuppressWarnings("rawtypes")
	//this http must be different than the one below
	@PutMapping("review/{id}")
	public ResponseEntity reviewOrder(@PathVariable int id, @RequestBody Orders order) {
		// don't have to check it (for null) as we are passing it to the next put so do not duplicate
		//check for < 50 or > 50 use a turneree statement to do both and set Status value
		var statusValue = (order.getTotal() <= 50) ? "APPROVED" : "REVIEW";
		order.setStatus(statusValue);
		return putOrder(id, order);	
		
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("approve/{id}")
	public ResponseEntity approveOrder(@PathVariable int id, @RequestBody Orders order ) {
		order.setStatus("APPROVED");
		return putOrder(id, order);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("reject/{id}")
	public ResponseEntity rejectOrder(@PathVariable int id, @RequestBody Orders order ) {
		order.setStatus("REJECTED");
		return putOrder(id, order);
	}
	
	//add put
		//SuppressWarnings allowed to use a generic type without returning for putOrder
		@SuppressWarnings("rawtypes")
		@PutMapping("{id}")
		public ResponseEntity putOrder(@PathVariable int id, @RequestBody Orders order){
			if (order == null || order.getId() == 0) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			// this makes sure we have an instance of order
			var ord = ordRepo.findById(order.getId());
			if(ord.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
			}
			ordRepo.save(order);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		//delete
		@SuppressWarnings("rawtypes")
		@DeleteMapping("{id}")
		public ResponseEntity deleteOrders(@PathVariable int id) {
			var orders = ordRepo.findById(id);
			if (orders.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			ordRepo.delete(orders.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);		
			
		}
}
