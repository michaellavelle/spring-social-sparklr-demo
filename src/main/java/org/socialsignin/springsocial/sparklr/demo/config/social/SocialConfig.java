package org.socialsignin.springsocial.sparklr.demo.config.social;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.socialsignin.springsocial.security.connect.SpringSocialSecurityConnectionFactory;
import org.socialsignin.springsocial.security.signup.SpringSocialSecurityConnectionSignUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.sparklr.api.Sparklr;
import org.springframework.social.sparklr.connect.SparklrConnectionFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableSocial
@PropertySource("classpath:/environment.properties")
public class SocialConfig implements SocialConfigurer {


	// Handle to users connection repository - allows us to set connection sign up in post construct
	private JdbcUsersConnectionRepository usersConnectionRepository;
	
	@Autowired(required=false)
	private SpringSocialSecurityConnectionSignUp springSocialSecurityConnnectionSignUp;


	
	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		return builder.setType(EmbeddedDatabaseType.H2).build();
	}
	
	
	@Bean 
	public DataSourceTransactionManager transactionManager(DataSource dataSource)
	{
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Override
	public void addConnectionFactories(
			ConnectionFactoryConfigurer cfConfig,
			Environment env) {
		 cfConfig.addConnectionFactory(new SparklrConnectionFactory(
	                env.getProperty("sparklr.clientId"),
	                env.getProperty("sparklr.clientSecret"),
	                env.getProperty("sparklr.authorizeUrl"),
	                env.getProperty("sparklr.tokenUrl"),
	                env.getProperty("sparklr.apiBaseUrl")
	        ));
	        cfConfig.addConnectionFactory(new SpringSocialSecurityConnectionFactory());
	}

	/**
	 * This is only needed because the official spring-social-security from SpringSocial is on the classpath
	 * @return
	 */
	@Override
	public UserIdSource getUserIdSource() {
		 return new AuthenticationNameUserIdSource();
	}
	
	
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(
			ConnectionFactoryLocator connectionFactoryLocator) {
		usersConnectionRepository  = new JdbcUsersConnectionRepository(
                dataSource(),
                connectionFactoryLocator,
               Encryptors.noOpText());
		return usersConnectionRepository;
		
	}
	

	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public Sparklr sparklr(ConnectionRepository repository) {
		Connection<Sparklr> connection = repository.findPrimaryConnection(Sparklr.class);
		return connection != null ? connection.getApi() : null;
	}
	
	
	
	@PostConstruct
	// Registers a mechanism for implicit sign up if user id available from provider
	// Remove if explicit user name selection is required
	public void registerConnectionSignUp()
	{
		usersConnectionRepository.setConnectionSignUp(springSocialSecurityConnnectionSignUp);
	}


}
