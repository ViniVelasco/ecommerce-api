package com.velasco.ecommerceapi.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class DBService {
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

	public void instantiateTestDatabase() throws ParseException {

		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");
		Category cat3 = new Category(null, "Cama mesa e banho");
		Category cat4 = new Category(null, "Eletrônicos");
		Category cat5 = new Category(null, "Jardinagem");
		Category cat6 = new Category(null, "Decoração");
		Category cat7 = new Category(null, "Perfumaria");
		
		Product p1 = new Product(null, "Computador", 2000.00);
		Product p2 = new Product(null, "Impressora", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);
		Product p4 = new Product(null, "Mesa de escritório", 300.00);
		Product p5 = new Product(null, "Toalha", 50.00);
		Product p6 = new Product(null, "Colcha", 200.00);
		Product p7 = new Product(null, "TV true color", 1200.00);
		Product p8 = new Product(null, "Roçadeira", 800.00);
		Product p9 = new Product(null, "Abajour", 100.00);
		Product p10 = new Product(null, "Pendente", 180.00);
		Product p11 = new Product(null, "Shampoo", 90.00);

		p1.getCategories().addAll(Arrays.asList(cat1, cat4));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategories().addAll(Arrays.asList(cat1, cat4));
		p4.getCategories().addAll(Arrays.asList(cat2));
		p5.getCategories().addAll(Arrays.asList(cat3));
		p6.getCategories().addAll(Arrays.asList(cat3));
		p7.getCategories().addAll(Arrays.asList(cat4));
		p8.getCategories().addAll(Arrays.asList(cat5));
		p9.getCategories().addAll(Arrays.asList(cat6));
		p10.getCategories().addAll(Arrays.asList(cat6));
		p11.getCategories().addAll(Arrays.asList(cat7));

		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2, p4));
		cat3.getProducts().addAll(Arrays.asList(p5, p6));
		cat4.getProducts().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProducts().addAll(Arrays.asList(p8));
		cat6.getProducts().addAll(Arrays.asList(p9, p10));
		cat7.getProducts().addAll(Arrays.asList(p11));

		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		State st1 = new State(null, "Minas Gerais");
		State st2 = new State(null, "São Paulo");

		City cit1 = new City(null, "Uberlândia", st1);
		City cit2 = new City(null, "São Paulo", st2);
		City cit3 = new City(null, "Campinas", st2);

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