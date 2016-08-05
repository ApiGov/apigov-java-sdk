package br.gov.serpro.apigov.javaclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Response;

import org.junit.Test;

import br.gov.serpro.apigov.javaclient.exceptions.NoMethodException;
import br.gov.serpro.apigov.javaclient.token.TokenAPI;

public class ApigovSDKTest {

	static String token = null;
	String sternaURL = "https://189.9.134.48/t/sterna.serpro/rest/1.0.0";

	@Test
	public void carregarConfig() {
		Config config = new Config();
		/* Estes dados estão no config.properties de testes apenas. */
		assertNotNull(config.tokenAPI);
		assertNotNull("consumerKey Nula no arquivo de properties", config.consumerKey);
		assertNotNull("consumerSecret Nula no arquivo de properties", config.consumerSecret);
	}
	
	@Test
	public void obterUmTokenValido() {		
		Config config = new Config();						
		token = TokenAPI.getValidToken(config.consumerKey, config.consumerSecret).access_token;
		assertNotNull("Token nulo, falhar na obtenção", token);
	}
	
	@Test
	public void requisitarUmSimplesGet() {
		ApigovSDK sternaAPI = new ApigovSDK(token, sternaURL);		
		Response response = sternaAPI.get("/catalogo/categorias").request();
		assertEquals(200, response.getStatus());
	}
	
	
	@Test(expected = NoMethodException.class) 
	public void chamarRequestSemInformarMetodo() {
		ApigovSDK sternaAPI = new ApigovSDK(token, sternaURL);		
		Response response = sternaAPI.request();
	}
	

}
