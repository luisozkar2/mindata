package com.challenge.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String clase;
	private final String atributo;
	private final long id;
	
	public ResourceNotFoundException(String clase, String atributo, Long id) {
		this.clase = clase;
		this.atributo = atributo;
		this.id = id;
	}
}