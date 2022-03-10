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
	@GetMapping({"id"})
	public ResponseEntity<Orders> getOrder(@PathVariable int id) {
		var Orders = ordRepo.findById(id);
		if(Orders.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Orders>(Orders.get(), HttpStatus.OK);
	}
	
	//add post
	@PostMapping
	public ResponseEntity<Orders> postOrder(@RequestBody Orders order) {
		if (order == null || order.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var ord = ordRepo.save(order);
		return new ResponseEntity<Orders>(ord, HttpStatus.CREATED);
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
		/*///delete
		@SuppressWarnings("rawtypes")
		@DeleteMapping("{id}")
		public ResponseEntity deleteOrder(@PathVariable int id) {
			var order = ordRepo.findById(id);
			if (order.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			ordRepo.delete(order.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);		
			
		}
		*/
}
