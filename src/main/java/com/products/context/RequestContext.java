package com.products.context;

import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.products.model.parameters.CreateProduct;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@NoArgsConstructor
public class RequestContext {

	private CreateProduct request;

	public void buildContext(CreateProduct request) {
		this.request = request;
	}
}
