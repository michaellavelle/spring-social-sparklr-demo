package org.socialsignin.provider.sparklr;

import org.socialsignin.springsocial.security.signin.SpringSocialSecurityConnectInterceptor;
import org.springframework.social.sparklr.api.Sparklr;
import org.springframework.stereotype.Component;

@Component
public class SparklrConnectInterceptor extends
		SpringSocialSecurityConnectInterceptor<Sparklr> {

}
