package com.velasco.ecommerceapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.velasco.ecommerceapi.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

}
