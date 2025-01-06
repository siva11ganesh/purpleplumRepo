package com.products.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.products.Repository.ProductContextRepository;
import com.products.context.RequestContext;
import com.products.exception.CustomException;
import com.products.model.parameters.CreateProduct;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

	@Autowired
	private RequestContext context;
	@Autowired
	private ProductContextRepository productRepo;

	public Object addProduct(CreateProduct request) {
		context.buildContext(request);
		log.info("Product Validation Starts");
		// If any validation logic
		log.info("Product Validation Ends");
		productRepo.save();
		return "Product Added Successfully";
	}

	public Page<CreateProduct> fetchProducts(String search, String sortBy, int page, int size,String orderBy) {
		log.info("Fetching Product Details");
		return productRepo.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(search, sortBy, page, size,orderBy);
	}

	public Object updateProduct(String id, CreateProduct productDetails) throws CustomException {
		productRepo.updateProduct(id, productDetails);
		log.info("Updated Product Details");
		return "Product Update Successfully";
	}

	public Object deleteProduct(String id) throws CustomException {
		String msg = "";
		int status = productRepo.deleteProductId(id);
		if (status == 1) {
			msg = "Product " + id + " deleted successfully";
			log.info("Product Detail deleted");
		}
		return msg;
	}

}
