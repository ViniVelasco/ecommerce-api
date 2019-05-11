package com.velasco.ecommerceapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.velasco.ecommerceapi.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
