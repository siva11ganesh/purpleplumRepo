package com.products.Repository;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.products.context.RequestContext;
import com.products.exception.CustomException;
import com.products.model.dto.ProductDto;
import com.products.model.parameters.CreateProduct;
import com.products.util.DAOUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional
public class ProductContextRepository {

	private final RequestContext context;
	private final ProductRepo productRepo;
	private final ModelMapper mapper;

	public void save() {
		ProductDto productDto = DAOUtil.mapDto(context);
		productRepo.save(productDto);
		log.info("Product Added into DB successfully");
	}

	public Page<CreateProduct> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String search,
			String sortBy, int page, int size, String orderBy) {
		Page<ProductDto> productDto = null;
		Sort sort = orderBy.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);

		if (search != null && !search.isEmpty()) {
			productDto = productRepo.findByProductNameContainingIgnoreCaseOrProductTypeContainingIgnoreCase(search,
					search, pageable);
		} else {
			productDto = productRepo.findAll(pageable);
		}

		return productDto.map(dto -> mapper.map(dto, CreateProduct.class));
	}

	public void updateProduct(String id, CreateProduct productDetails) throws CustomException {
		ProductDto existingProduct = productRepo.findByProductId(id)
				.orElseThrow(() -> new CustomException("Product not found with id: " + id));

		existingProduct.setProductName(productDetails.getProductName());
		existingProduct.setProductType(productDetails.getProductType());
		existingProduct.setProductMrp(productDetails.getProductMrp());
		existingProduct.setTotalProductCount(productDetails.getTotalProductCount());
		existingProduct.setSoldCount(productDetails.getSoldCount());

		// Save updated product
		productRepo.save(existingProduct);
	}

	public int deleteProductId(String id) throws CustomException {
		if (!productRepo.existsByProductId(id)) {
			throw new CustomException("Product not found with id: " + id);
		}
		return productRepo.deleteProductId(id);
	}
}
