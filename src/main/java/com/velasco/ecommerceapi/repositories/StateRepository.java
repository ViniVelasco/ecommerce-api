package com.velasco.ecommerceapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.velasco.ecommerceapi.domain.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {

}
