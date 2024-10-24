package br.com.branetlogistica.core.security.client;

import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.URIScheme;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateCustom {

	@Bean
	public RestTemplate restTemplate() {

		try {

			SSLContext sslContext = SSLContextBuilder.create()
					.loadTrustMaterial((X509Certificate[] certificateChain, String authType) -> true)
					.build();

			Registry<ConnectionSocketFactory> socketRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register(URIScheme.HTTPS.getId(), new SSLConnectionSocketFactory(sslContext))
					.register(URIScheme.HTTP.getId(), new PlainConnectionSocketFactory()).build();

			HttpClient httpClient = HttpClientBuilder.create()
					.setConnectionManager(new PoolingHttpClientConnectionManager(socketRegistry))
					.setConnectionManagerShared(true).build();

			ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

			return new RestTemplate(requestFactory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
