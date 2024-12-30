package com.products.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.products.context.RequestContext;
import com.products.model.dto.ProductDto;

public class DAOUtil {

	private static final ModelMapper mapper;
	static {
		mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	public static ProductDto mapDto(RequestContext context) {
		ProductDto productDto = new ProductDto();
		mapper.map(context.getRequest(), productDto);
		return productDto;
	}
}
