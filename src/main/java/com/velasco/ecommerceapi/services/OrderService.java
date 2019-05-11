package com.velasco.ecommerceapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.velasco.ecommerceapi.domain.Order;
import com.velasco.ecommerceapi.repositories.OrderRepository;
import com.velasco.ecommerceapi.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repo;
	
	public Order find(Integer id) {
		Optional<Order> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo " + Order.class.getName()));
	}

}
