package br.gov.serpro.apigov.javaclient.exceptions;

import br.gov.serpro.apigov.javaclient.ApigovSDK;

public class NoMethodException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoMethodException() {
		super("Informe qual o tipo de requisição: "+ApigovSDK.METHOD.values());
	}
	
}
