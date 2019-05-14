package com.velasco.ecommerceapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.velasco.ecommerceapi.domain.Address;
import com.velasco.ecommerceapi.domain.City;
import com.velasco.ecommerceapi.domain.Client;
import com.velasco.ecommerceapi.domain.enums.ClientType;
import com.velasco.ecommerceapi.domain.enums.Profile;
import com.velasco.ecommerceapi.dto.ClientDTO;
import com.velasco.ecommerceapi.dto.ClientNewDTO;
import com.velasco.ecommerceapi.repositories.AddressRepository;
import com.velasco.ecommerceapi.repositories.ClientRepository;
import com.velasco.ecommerceapi.security.UserSS;
import com.velasco.ecommerceapi.services.exceptions.AuthorizationException;
import com.velasco.ecommerceapi.services.exceptions.DataIntegrityException;
import com.velasco.ecommerceapi.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;
	
	@Autowired
	private ClientRepository repo;
	
	@Autowired
	private AddressRepository addressRepository;
	
	public Client find(Integer id) {
		
		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Profile.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Client> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo " + Client.class.getName()));
	}
	
	public Client insert(Client obj) {
		obj.setId(null); //new object guarantee
		obj = repo.save(obj);
		addressRepository.saveAll(obj.getAddress());
		return obj;
	}
	
	public Client udpate(Client obj) {
		Client newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas!");
		}
	}
	
	public List<Client> findAll() {
		return repo.findAll();
	}
	
	public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page,  linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Client fromDTO(ClientDTO objDto) {
		return new Client(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null, null);
	}
	
	public Client fromDTO(ClientNewDTO objDto) { //sobrecarga
		Client cli = new Client(null, objDto.getName(), objDto.getEmail(), objDto.getCpfCnpj(), ClientType.toEnum(objDto.getType()), bCrypt.encode(objDto.getPassword()));
		City city = new City(objDto.getCityId(), null, null);
		Address adr = new Address(null, objDto.getLogradouro(), objDto.getNumber(), objDto.getComplement(), objDto.getBairro(), objDto.getCep(), cli, city);
		cli.getAddress().add(adr);
		cli.getPhones().add(objDto.getPhone1());
		
		if(objDto.getPhone2() != null) {
			cli.getPhones().add(objDto.getPhone2());
		}
		if(objDto.getPhone3() != null) {
			cli.getPhones().add(objDto.getPhone3());
		}
		return cli;
	}

	private void updateData(Client newObj, Client obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}
	
}
