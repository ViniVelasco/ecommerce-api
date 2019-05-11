package com.velasco.ecommerceapi.domain;

import javax.persistence.Entity;

import com.velasco.ecommerceapi.domain.enums.PaymentStatus;

@Entity
public class CardPayment  extends Payment {
	private static final long serialVersionUID = 1L;
	
	private Integer portionsNumber;
	
	public CardPayment() {
		
	}

	public CardPayment(Integer id, PaymentStatus state, Order order, Integer portionsNumber) {
		super(id, state, order);
		this.portionsNumber = portionsNumber;
	}

	public Integer getPortionsNumber() {
		return portionsNumber;
	}

	public void setPortionsNumber(Integer portionsNumber) {
		this.portionsNumber = portionsNumber;
	}
	
	

}
