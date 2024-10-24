package br.com.branetlogistica.core.database.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import br.com.branetlogistica.core.database.migration.FlywayMigrationInitializer;
import br.com.branetlogistica.core.database.provider.IntegratorImpl;

@Configuration
public class HibernateConfig {

	@Autowired
	private JpaProperties jpaProperties;

	@Value("${app.datasource.schema}")
	private String schemaName;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddlAuto;

	@Value("${spring.jpa.show-sql}")
	private boolean showSql;
	
	@Bean
	JpaVendorAdapter jpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}

	/*
	 * @Bean CustomInterceptorImpl customInterceptorImpl() { return new
	 * CustomInterceptorImpl(); }
	 */

	@Bean
	IntegratorImpl integrator() {
		return IntegratorImpl.getInstance();
	}

	@Bean
	IntegratorProviderImpl integrators() {
		return new IntegratorProviderImpl();
	}

	@Bean(initMethod = "migrate")
	FlywayMigrationInitializer flywayMigrationInitializer() {
		return new FlywayMigrationInitializer();
	}

	@Bean
	@DependsOn(value = "flywayMigrationInitializer")
	LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
			MultiTenantConnectionProvider<?> multiTenantConnectionProviderImpl,
			CurrentTenantIdentifierResolver<?> currentTenantIdentifierResolverImpl) {

		Map<String, Object> jpaPropertiesMap = new HashMap<>(jpaProperties.getProperties());
		jpaPropertiesMap.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProviderImpl);
		jpaPropertiesMap.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolverImpl);
		jpaPropertiesMap.put(Environment.DEFAULT_SCHEMA, schemaName);
		jpaPropertiesMap.put(Environment.FORMAT_SQL, showSql);
		jpaPropertiesMap.put(Environment.SHOW_SQL, showSql);
		jpaPropertiesMap.put(Environment.HBM2DDL_AUTO, ddlAuto);

//        jpaPropertiesMap.put(Environment.HBM2DDL_CREATE_SCHEMAS, "true");        
//        jpaPropertiesMap.put(Environment.HBM2DDL_AUTO,"create-drop");       
//        jpaPropertiesMap.put(Environment.AUTO_CLOSE_SESSION,"true");
//       // jpaPropertiesMap.put(Environment.AUTO_SESSION_EVENTS_LISTENER,true);
//        //jpaPropertiesMap.put("hibernate.integrator_provider", "br.com.branetlogistica.productbase.core.tenancy.interceptor.IntegratorProviderImpl");
//       jpaPropertiesMap.put("hibernate.integrator_provider", this.integrators());

		// jpaPropertiesMap.put(Environment.JPA_PERSISTENCE_PROVIDER,
		// this.integrator());
		// Integrator
		// jpaPropertiesMap.put(Environment.INTERCEPTOR, customInterceptorImpl() );
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPackagesToScan("br.com.branetlogistica");
		em.setJpaVendorAdapter(this.jpaVendorAdapter());
		em.setJpaPropertyMap(jpaPropertiesMap);

		return em;
	}
}