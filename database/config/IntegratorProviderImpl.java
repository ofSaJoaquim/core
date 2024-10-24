package br.com.branetlogistica.core.database.config;

import java.util.Arrays;
import java.util.List;

import org.hibernate.integrator.spi.Integrator;
import org.hibernate.jpa.boot.spi.IntegratorProvider;

import br.com.branetlogistica.core.database.provider.IntegratorImpl;

public class IntegratorProviderImpl implements IntegratorProvider {

	private IntegratorImpl integratorImpl;

	@Override
	public List<Integrator> getIntegrators() {
		return Arrays.asList(integratorImpl);
	}

	public IntegratorProviderImpl() {
		super();
		this.integratorImpl = IntegratorImpl.getInstance();

	}

}