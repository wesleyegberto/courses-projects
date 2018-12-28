package com.github.wesleyegberto.tollservice;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthCheck implements HealthIndicator {
	private int errorCode;

	@Override
	public Health health() {
		errorCode++;
		System.out.println("Health check performed, value is " + errorCode);
		if (errorCode > 4 && errorCode < 8) {
			return Health.down()
				.withDetail("error", "Error code is between 4 and 8")
				.build();
		}
		return Health.up()
			.withDetail("status", errorCode)
			.build();
	}

}
