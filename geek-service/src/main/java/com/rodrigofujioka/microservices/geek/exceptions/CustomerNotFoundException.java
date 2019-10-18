package com.rodrigofujioka.microservices.geek.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public CustomerNotFoundException(String mensagem) {
		super("No such customer: " + mensagem);
	}
	
}
