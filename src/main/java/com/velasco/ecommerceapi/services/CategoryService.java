package com.velasco.ecommerceapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.velasco.ecommerceapi.domain.Category;
import com.velasco.ecommerceapi.repositories.CategoryRepository;
import com.velasco.ecommerceapi.services.exceptions.DataIntegrityException;
import com.velasco.ecommerceapi.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repo;
	
	public Category find(Integer id) {
		Optional<Category> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo " + Category.class.getName()));
	}
	
	public Category insert(Category obj) {
		obj.setId(null); //new object guarantee
		return repo.save(obj);
	}

	public Category udpate(Category obj) {
		find(obj.getId());
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos!");
		}
	}

}
