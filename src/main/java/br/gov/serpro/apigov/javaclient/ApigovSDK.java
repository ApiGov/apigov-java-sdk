package br.gov.serpro.apigov.javaclient;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.gov.serpro.apigov.javaclient.exceptions.NoMethodException;
import br.gov.serpro.apigov.javaclient.internal.ClientFactory;

/**
 * Classe para disponibilizar o acesso as API's fornecidas pelo
 * https://apigov.serpro.gov.br
 *
 */
public class ApigovSDK {

	private Client client;
	private String token;
	private String uri;
	private String path;
	private Map<String, String> queryParams = new LinkedHashMap<String, String>();

	public enum METHOD {
		GET, POST;
	}

	private METHOD method;
	private Form formData;

	public ApigovSDK(String token, String uri) {
		this.token = token;
		this.uri = uri;
		this.client = ClientFactory.createClient();
	}

	/**
	 * Adiciona os parametros no final da url.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public ApigovSDK queryParam(String key, String value) {
		queryParams.put(key, value);
		return this;
	}

	/**
	 * Prepara para executar um GET no servidor para o caminho informado no
	 * path.
	 * 
	 * QueryParameters vem depois deste caminho.
	 * 
	 * @param path
	 * @return
	 */
	public ApigovSDK get(String path) {
		this.method = METHOD.GET;
		this.path = path;
		return this;
	}

	public ApigovSDK post(String path, Form formData) {
		this.method = METHOD.POST;
		this.formData = formData;
		return this;

	}

	/**
	 * Submete a requisição;
	 * 
	 * @return
	 */
	public Response request() {
		if(this.method == null)
			throw new NoMethodException();
		
		
		Builder builder = createBuilder(path);
		Response response = null;
		if (METHOD.GET.equals(this.method)) {
			response = builder.get(Response.class);
		} else if (METHOD.POST.equals(this.method)) {
			response = builder.post(Entity.entity(formData, MediaType.APPLICATION_FORM_URLENCODED_TYPE),
					Response.class);
		}
		handleStatus(response);
		clearData();
		return response;
	}

	private void clearData() {
		path = null;
		queryParams.clear();
		method = null;
		formData = null;
	}

	private Builder createBuilder(String path) {
		WebTarget target = client.target(uri);
		if (path != null) {
			target = target.path(path);
		}
		if (queryParams != null) {
			for (Map.Entry<String, String> entry : queryParams.entrySet()) {
				target = target.queryParam(entry.getKey(), entry.getValue());
			}
			queryParams.clear();
		}

		Builder builder = target.request("application/json");
		if (token != null) {
			builder = builder.header("Authorization", "Bearer " + token);
		}
		return builder;
	}

	private void handleStatus(Response response) {
		if (response.getStatus() < 200 || response.getStatus() >= 300) {
			System.err.println(response.readEntity(String.class));
			throw new RuntimeException(response.readEntity(String.class));
		}
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{\n");
		sb.append("  'token':'"+this.token+"',\n");
		sb.append("  'uri':'"+this.uri+"',\n");
		sb.append("  'path':'"+this.path+"',\n");
		sb.append("  'method':'"+this.method+"',\n");
		sb.append("  'queryParams':'"+this.queryParams+"',\n");
		sb.append("  'formData':'"+this.formData+"'\n");
		sb.append("}");
		return sb.toString();
	}

}
