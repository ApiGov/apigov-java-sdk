package br.gov.serpro.apigov.javaclient.token;

import java.util.Base64;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import br.gov.serpro.apigov.javaclient.Config;
import br.gov.serpro.apigov.javaclient.internal.ClientFactory;

/**
 * Esta classe mostra como renovar o token de acesso ao servidor.
 * 
 * Para isso é preciso do consumerKey e consumerSecret.
 * 
 * Evite expor estes dados em sua aplicação cliente diretamente, para evitar 
 * o uso indevido de sua conta.
 *
 */
public class TokenAPI {

	private static Config config = new Config();
	
	/**
	 * Busca no servidor o Token de acesso válido.
	 * 
	 * Esta funcionalidade deve preferencialmente ficar em ambiente seguro. Para evitar
	 * que utilizem suas credenciais, não as deixe visíves no código cliente (Android, Javascript, iOS...)
	 * 
	 * 
	 * @param key
	 * @param secret
	 * @return Token
	 */
	public static Token getValidToken(String key,String secret) {
			WebTarget target = ClientFactory.createClient().target(config.tokenAPI);
			Builder builder = target.request("application/json");			
			String credentials = key+":"+secret;
			byte[] decodedBytes = Base64.getEncoder().encode(credentials.getBytes());	
			builder.header("Authorization","Basic "+new String(decodedBytes));	
			Form form = new Form();
			form.param("grant_type", "client_credentials");	
			Token token = builder.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE),Token.class);
			System.out.println("Novo token: "+token.access_token +" Expira em: "+token.expires_in);
		return token;
	}
	
	public static class Token{
		public Token() {}
		public String scope;
		public String token_type;
		public Long expires_in;
		public String access_token;
	}
	
}
