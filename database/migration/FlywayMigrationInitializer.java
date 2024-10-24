package br.com.branetlogistica.core.database.migration;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;

import br.com.branetlogistica.core.context.Context;

public class FlywayMigrationInitializer {

	@Value("${spring.datasource.hikari.schema}")
	private String schemaName;

	public void migrate() {
		this.migrate((DataSource) null);
	}

	public void migrate(DataSource dataSource) {
		String scriptLocation = "db/migration";
		String UUID = "BRANET";

		if (Context.getContextData() != null)
			UUID = Context.getContextData().getRequest().getClientId();

		if (!UUID.equals("BRANET") && dataSource != null) {
			Flyway flyway = Flyway.configure().locations(scriptLocation).baselineOnMigrate(Boolean.TRUE)
					.dataSource(dataSource).schemas(schemaName).load();

			flyway.migrate();
		}

	}
}