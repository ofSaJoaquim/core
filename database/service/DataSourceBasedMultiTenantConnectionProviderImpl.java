package br.com.branetlogistica.core.database.service;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl
		extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Value("${spring.profiles.active}")
	private String profile;
	
	@Autowired
	private DataSource defaultDS;

	@Autowired
	private ApplicationContext context;

	private Map<String, DataSource> map = new HashMap<>();

	boolean init = false;

	@PostConstruct
	public void load() {
		map.put("branet", defaultDS);
	}

	@Override
	protected DataSource selectAnyDataSource() {
		return map.get("branet");
	}

	public DataSource getDataSourceByTenant(String tenant) {
		return (DataSource) map.get(tenant);
	}

	@Override
	protected DataSource selectDataSource(String clientId) {
		
		
		DataSource dataSource = map.get(isDevMode()?"branet":clientId);

		if (dataSource == null) {
			TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
			dataSource = tenantDataSource.createDataSource(clientId);
			map.put(clientId, dataSource);
			return dataSource;
		}
		return dataSource;

	}
	
	private boolean isDevMode() {
		if(profile == null || profile.isEmpty() || profile.isBlank())
			return false;
		if(profile.equals("dev"))
			return true;
		return false;
	}
	
	

}