package com.velasco.ecommerceapi.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.velasco.ecommerceapi.domain.BoletoPayment;

@Service
public class BoletoService {

	public void giveDueDate(BoletoPayment pay, Date instant) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instant);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pay.setDueDate(cal.getTime());
	}
}
