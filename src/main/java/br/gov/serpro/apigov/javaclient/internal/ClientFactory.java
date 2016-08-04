package br.gov.serpro.apigov.javaclient.internal;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.jackson.JacksonFeature;

/**
 * Cria um cliente SSL simplificado.
 * 
 * Está habilitado o JacksonFeature para converter Json em Objeto.
 * 
 * Não utiliza um certificado real e confiando em qualquer host.
 *
 */
public class ClientFactory {

	private static SSLContext configureCtx() {
		TrustManager[] certs = new TrustManager[] { new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		} };
		SSLContext ctx = null;
		try {
			ctx = SSLContext.getInstance("TLS");
			ctx.init(null, certs, new SecureRandom());
		} catch (java.security.GeneralSecurityException ex) {
		}
		HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
		return ctx;
	}

	/**
	 * 
	 * Instancia um cliente através da factory ClientBuilder confiando em qualquer host. 
	 * 
	 * @return Client
	 */
	public static Client createClient() {
		return ClientBuilder.newBuilder().hostnameVerifier(new TrustAllHostNameVerifier()).sslContext(configureCtx())
				.register(JacksonFeature.class).build();
	}

	public static class TrustAllHostNameVerifier implements HostnameVerifier {

		public boolean verify(String hostname, SSLSession session) {
			return true;
		}

	}

}
