package com.velasco.ecommerceapi;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.velasco.ecommerceapi.domain.Address;
import com.velasco.ecommerceapi.domain.BoletoPayment;
import com.velasco.ecommerceapi.domain.CardPayment;
import com.velasco.ecommerceapi.domain.Category;
import com.velasco.ecommerceapi.domain.City;
import com.velasco.ecommerceapi.domain.Client;
import com.velasco.ecommerceapi.domain.Order;
import com.velasco.ecommerceapi.domain.OrderItem;
import com.velasco.ecommerceapi.domain.Payment;
import com.velasco.ecommerceapi.domain.Product;
import com.velasco.ecommerceapi.domain.State;
import com.velasco.ecommerceapi.domain.enums.ClientType;
import com.velasco.ecommerceapi.domain.enums.PaymentStatus;
import com.velasco.ecommerceapi.repositories.AddressRepository;
import com.velasco.ecommerceapi.repositories.CategoryRepository;
import com.velasco.ecommerceapi.repositories.CityRepository;
import com.velasco.ecommerceapi.repositories.ClientRepository;
import com.velasco.ecommerceapi.repositories.OrderItemRepository;
import com.velasco.ecommerceapi.repositories.OrderRepository;
import com.velasco.ecommerceapi.repositories.PaymentRepository;
import com.velasco.ecommerceapi.repositories.ProductRepository;
import com.velasco.ecommerceapi.repositories.StateRepository;


@SpringBootApplication
public class EcommerceApiApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");
		
		Product p1 = new Product(null, "Computador", 2000.00);
		Product p2 = new Product(null, "Impressora", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);
		
		categoryRepository.saveAll(Arrays.asList(cat1, cat2));
		productRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		State st1 = new State(null, "Minas Gerais");
		State st2 = new State(null, "São Paulo");
		
		City cit1 = new City(null, "Uberlândia", st1);
		City cit2 = new City(null, "São Paulo", st2);
		City cit3 = new City(null, "Campinas", st2);
		
		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2));
		
		p1.getCategories().addAll(Arrays.asList(cat1));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2));
		p3.getCategories().addAll(Arrays.asList(cat1));
		
		st1.getCities().addAll(Arrays.asList(cit1));
		st2.getCities().addAll(Arrays.asList(cit2, cit3));

		stateRepository.saveAll(Arrays.asList(st1, st2));
		cityRepository.saveAll(Arrays.asList(cit1, cit2, cit3));
		
		Client cli1 = new Client(null, "Maria Silva", "maria@gmail.com", "36378912377", ClientType.PESSOAFISICA);
		
		cli1.getPhones().addAll(Arrays.asList("23631302833", "319379814"));
		
		Address a1 = new Address(null, "Rua Flores", "300", "Apt 303", "Jardim", "3883713", cli1, cit1);
		Address a2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, cit2);
	
		cli1.getAddress().addAll(Arrays.asList(a1, a2));
		
		clientRepository.saveAll(Arrays.asList(cli1));
		addressRepository.saveAll(Arrays.asList(a1, a2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Order ped1 = new Order(null, sdf.parse("30/09/2017 10:32"), cli1, a1);
		Order ped2 = new Order(null, sdf.parse("10/10/2017 19:32"), cli1, a2);
	
		Payment pay1 = new CardPayment(null, PaymentStatus.QUITADO, ped1, 6);
		ped1.setPayment(pay1);
		
		Payment pay2 = new BoletoPayment(null, PaymentStatus.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPayment(pay2);
		
		cli1.getOrders().addAll(Arrays.asList(ped1, ped2));
		
		orderRepository.saveAll(Arrays.asList(ped1, ped2));
		paymentRepository.saveAll(Arrays.asList(pay1, pay2));
		
		OrderItem ip1 = new OrderItem(ped1, p1, 0.00, 1, 2000.00);
		OrderItem ip2 = new OrderItem(ped1, p3, 0.00, 2, 80.00);
		OrderItem ip3 = new OrderItem(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItems().addAll(Arrays.asList(ip1, ip2));
		ped2.getItems().addAll(Arrays.asList(ip3));
		
		p1.getItems().addAll(Arrays.asList(ip1));
		p2.getItems().addAll(Arrays.asList(ip3));
		p3.getItems().addAll(Arrays.asList(ip2));
		
		orderItemRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
