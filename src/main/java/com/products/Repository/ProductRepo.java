package com.products.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.products.model.dto.ProductDto;

@Repository
public interface ProductRepo extends JpaRepository<ProductDto, Integer> {

	Page<ProductDto> findByProductNameContainingIgnoreCaseOrProductTypeContainingIgnoreCase(String productName,
			String productType, Pageable pageable);

	Optional<ProductDto> findByProductId(String productId);

	@Modifying
	@Transactional
	@Query("DELETE FROM ProductDto p WHERE p.productId = :id")
	int deleteProductId(String id);

	boolean existsByProductId(String id);
}
