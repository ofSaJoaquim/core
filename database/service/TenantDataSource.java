package br.com.branetlogistica.core.database.service;

import java.io.Serializable;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import br.com.branetlogistica.core.database.migration.FlywayMigrationInitializer;
import br.com.branetlogistica.core.tenant.model.Tenant;
import br.com.branetlogistica.core.tenant.service.TenantService;

@Component
public class TenantDataSource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Value("${app.datasource.schema}")
	private String nomeAplicativo;

	@Value("${app.datasource.username}")
	private String username;

	@Value("${app.datasource.password}")
	private String password;
	
	@Value("${app.datasource.url}")
	private String url;

	@Value("${app.datasource.driverClassName}")
	private String driver;
	
	@Autowired
	private FlywayMigrationInitializer context;
	
	@Autowired
	private TenantService tenantService;

	public DataSource createDataSource(String clientId) {
		Tenant tenant = tenantService.findTenantByClientId(clientId);
		try {
			ConnectionFactory.checkAndCreate("db_"+tenant.getClientId(), url, password, username);
			@SuppressWarnings("rawtypes")
			DataSourceBuilder factory = 
			DataSourceBuilder.create()
				.driverClassName(driver)
				.username(username)
				.password(password)
				.url(url+"/"+"db_"+clientId);
			DataSource ds = factory.build();
			context.migrate(ds);
			return ds;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
