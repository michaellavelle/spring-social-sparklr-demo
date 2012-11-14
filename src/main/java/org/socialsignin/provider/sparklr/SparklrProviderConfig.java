package org.socialsignin.provider.sparklr;

import org.socialsignin.provider.AbstractProviderConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.sparklr.api.Sparklr;
import org.springframework.social.sparklr.connect.SparklrConnectionFactory;

@Configuration
public class SparklrProviderConfig extends AbstractProviderConfig<Sparklr> {
	
	
	@Autowired
	private SparklrConnectInterceptor sparklrConnectInterceptor;
	
	@Value("${sparklr.clientId}")
	private String sparklrClientId;

	@Value("${sparklr.clientSecret}")
	private String sparklrClientSecret;
	
	@Value("${sparklr.authorizeUrl}")
	private String oauthAuthorizeUrl; 
	
	@Value("${sparklr.tokenUrl}")
	private String oauthTokenUrl;
	
	@Value("${sparklr.apiBaseUrl}")
	private String oauthApiBaseUrl;
	

	@Override
	protected ConnectionFactory<Sparklr> createConnectionFactory() {
		return new SparklrConnectionFactory(
				sparklrClientId, sparklrClientSecret,oauthAuthorizeUrl,oauthTokenUrl,oauthApiBaseUrl);
	}

	@Override
	protected ConnectInterceptor<Sparklr> getConnectInterceptor() {
		return sparklrConnectInterceptor;
	}
	

}
