package com.products.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.products.exception.CustomException;
import com.products.model.parameters.CreateProduct;
import com.products.service.PdfService;
import com.products.service.ProductService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private PdfService pdfService;

	@PostMapping(value = "/addproduct", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addProduct(@RequestBody @Valid CreateProduct request) {
		log.info("Add Product --> Starts");
		Object successMsg = productService.addProduct(request);
		log.info("Add Product --> Completed");
		return ResponseEntity.status(HttpStatus.OK).body(successMsg);
	}

	@GetMapping("/fetchproduct")
	public Page<CreateProduct> getProducts(@RequestParam(required = false) String search,
			@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "asc") String orderBy) {
		return productService.fetchProducts(search, sortBy, page, size, orderBy);
	}

	@PutMapping(value = "/updateproduct/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateProduct(@PathVariable String productId,
			@RequestBody @Valid CreateProduct request) throws CustomException, Exception {
		Object successMsg = productService.updateProduct(productId, request);
		return ResponseEntity.status(HttpStatus.OK).body(successMsg);
	}

	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<Object> deleteProduct(@PathVariable String productId) throws CustomException, Exception {
		Object successMsg = productService.deleteProduct(productId);
		return ResponseEntity.status(HttpStatus.OK).body(successMsg);
	}

	@GetMapping("/generate-pdf")
	public ResponseEntity<byte[]> generatePdf() throws Exception {
		byte[] pdfContent = pdfService.generatePdf();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline;filename=generated.pdf");
		headers.add("content-Type", MediaType.APPLICATION_PDF_VALUE);
		return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
	}
}
