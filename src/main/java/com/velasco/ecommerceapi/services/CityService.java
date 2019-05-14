package com.velasco.ecommerceapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.velasco.ecommerceapi.domain.City;
import com.velasco.ecommerceapi.repositories.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository repo;
	
	public List<City> findByState(Integer stateId) {
		return repo.findCities(stateId);
	}
}
