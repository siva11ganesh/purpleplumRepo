package com.products.model.parameters;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateProduct {

	@NotNull(message = "Product ID must not be null")
	@NotBlank(message = "Product ID must not be Blank")
	private String productId;
	@NotNull(message = "Product Name must not be null")
	@NotBlank(message = "Product Name must not be Blank")
	private String productName;
	@NotNull(message = "Product Type must not be null")
	@NotBlank(message = "Product Type must not be Blank")
	private String productType;
	@NotNull(message = "Product MRP must not be null")
	@Min(value = 1, message = "Invalid MRP")
	private double productMrp;
	private int totalProductCount;
	private int soldCount;
}
