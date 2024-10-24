package br.com.branetlogistica.core.client;



import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("deprecation")
@Configuration
public class CustomRestTemplateConfiguration {

    
    private static String trustStore;

   
    private static String trustStorePassword;

    
    @Value("${trust.store}")
    private void setTrustStore(String trusStore) {
    	CustomRestTemplateConfiguration.trustStore = trusStore;
    }
    
    @Value("${trust.store.password}")
    private void setTrustStorePassword( String trustStorePassword) {
    	CustomRestTemplateConfiguration.trustStorePassword = trustStorePassword;
    }

	public static RestTemplate restTemplate() {
		try {
			
			 KeyStore clientStore = KeyStore.getInstance("PKCS12");
             File file = ResourceUtils.getFile(trustStore);
             
		     clientStore.load(new FileInputStream(file), trustStorePassword.toCharArray());

			SSLContext sslContext = new 
					SSLContextBuilder()
					//.setProtocol("PKCS12")
					.loadKeyMaterial(clientStore,  trustStorePassword.toCharArray())
					.loadTrustMaterial(new TrustSelfSignedStrategy()).build();
			SSLConnectionSocketFactory sslConFactory = new SSLConnectionSocketFactory(sslContext);

			final HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
					.setSSLSocketFactory(sslConFactory).build();

			CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
			ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
			return new RestTemplate(requestFactory);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
       
    }
}