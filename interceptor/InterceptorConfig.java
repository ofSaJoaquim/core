package br.com.branetlogistica.core.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.branetlogistica.core.context.ContextUtil;

@Configuration
@EnableWebMvc
public class InterceptorConfig implements WebMvcConfigurer {


	@Autowired
	private ContextUtil contextUtil;
	
	@Autowired
	private PosInterceptor posInterceptor;

	@Bean
	public InterceptorRequest interceptorRequest() {
		return new InterceptorRequest(contextUtil, posInterceptor);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptorRequest());
	}

	
	
}
