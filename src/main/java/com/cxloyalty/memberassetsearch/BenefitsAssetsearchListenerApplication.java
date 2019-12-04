package com.cxloyalty.memberassetsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.affinion.gce.security.adapter.OauthTemplate;
import com.affinion.gce.security.config.SecurityConfigurer;

/**
 * @author afall
 *
 */
@ComponentScan(basePackages = {"com.cxloyalty.*"})
@Import({ SecurityConfigurer.class, OauthTemplate.class, SecurityConfigurer.OAuthRestTemplateConfigurer.class})
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class, DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})

public class BenefitsAssetsearchListenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BenefitsAssetsearchListenerApplication.class);
	}

}
