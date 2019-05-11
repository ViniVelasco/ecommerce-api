package com.velasco.ecommerceapi.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.velasco.ecommerceapi.domain.enums.PaymentStatus;

@Entity
public class BoletoPayment extends Payment {
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dueDate;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date paymentDate;
	
	public BoletoPayment() {
		
	}

	public BoletoPayment(Integer id, PaymentStatus state, Order order, Date dueDate, Date paymentDate) {
		super(id, state, order);
		this.dueDate = dueDate;
		this.paymentDate = paymentDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	

}
