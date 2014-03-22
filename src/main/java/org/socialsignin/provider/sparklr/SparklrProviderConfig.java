package org.socialsignin.provider.sparklr;

import org.socialsignin.provider.AbstractProviderConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.sparklr.api.Sparklr;

@Configuration
public class SparklrProviderConfig extends AbstractProviderConfig<Sparklr> {
	
	@Autowired
	private ConnectInterceptor<Sparklr> sparklrConnectInterceptor;

	@Override
	protected ConnectInterceptor<Sparklr> getConnectInterceptor() {
		return sparklrConnectInterceptor;
	}
	
}
