package com.velasco.ecommerceapi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.velasco.ecommerceapi.domain.Category;
import com.velasco.ecommerceapi.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	//@Query("SELECT DISTINCT obj from Product obj INNER JOIN obj.categories cat where obj.name LIKE %:name% AND cat in :categories") @PARAM("name") @Param("categories")
	@Transactional(readOnly=true)
	Page<Product> findDistinctByNameContainingAndCategoriesIn(String name, List<Category> categories, Pageable pageRequest);
}
