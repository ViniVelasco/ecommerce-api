package com.velasco.ecommerceapi.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.velasco.ecommerceapi.domain.BoletoPayment;
import com.velasco.ecommerceapi.domain.Client;
import com.velasco.ecommerceapi.domain.Order;
import com.velasco.ecommerceapi.domain.OrderItem;
import com.velasco.ecommerceapi.domain.enums.PaymentStatus;
import com.velasco.ecommerceapi.repositories.OrderItemRepository;
import com.velasco.ecommerceapi.repositories.OrderRepository;
import com.velasco.ecommerceapi.repositories.PaymentRepository;
import com.velasco.ecommerceapi.security.UserSS;
import com.velasco.ecommerceapi.services.exceptions.AuthorizationException;
import com.velasco.ecommerceapi.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private EmailService emailService;
	
	public Order find(Integer id) {
		Optional<Order> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo " + Order.class.getName()));
	}

	@Transactional
	public Order insert(Order obj) {
		obj.setId(null);
		obj.setInstant(new Date());
		obj.setClient(clientService.find(obj.getClient().getId()));
		obj.getPayment().setState(PaymentStatus.PENDENTE);
		obj.getPayment().setOrder(obj);
		if (obj.getPayment() instanceof BoletoPayment) {
			BoletoPayment paym = (BoletoPayment) obj.getPayment();
			boletoService.giveDueDate(paym, obj.getInstant());
		}
		
		obj = repo.save(obj);
		paymentRepository.save(obj.getPayment());
		for(OrderItem ip : obj.getItems()) {
			ip.setDiscount(0.0);
			ip.setProduct(productService.find(ip.getProduct().getId()));
			ip.setPrice(ip.getProduct().getPrice());
			ip.setOrder(obj);
		}
		
		orderItemRepository.saveAll(obj.getItems());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	public Page<Order> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page,  linesPerPage, Direction.valueOf(direction), orderBy);
		Client client = clientService.find(user.getId());
		return repo.findByClient(client, pageRequest);
	}

}
