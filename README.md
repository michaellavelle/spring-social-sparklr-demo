Spring Social Sparklr Demo
===========================

Simple Hello World Webapp demonstrating the <a href="https://github.com/michaellavelle/spring-social-sparklr">
spring-social-sparklr</a> and <a href="https://github.com/socialsignin/spring-social-security">
spring-social-security</a> modules.

Requires version 1.0.1-SNAPSHOT of the Sparklr application from spring-security-oauth ( https://github.com/SpringSource/spring-security-oauth ) 
to be running on port 8080.


Background
----------


Resources in the application are protected using provider specific roles such has ROLE_USER_SPARKLR, or
simply by ROLE_USER.

Spring Security is configured with the SpringSocialSecurityAuthenticationFilter which ensures that users attempting to
access a protected resource are prompted to connect with the relevant SaaS provider in order to authenticate.  

Once authenticated, users confirm their chosen username, a account is created for them, and they can access the protected resource.

Once a user has an account, they can login to the system any time by simply reconnecting with any of the providers
they have previously connected with the app previously.

Local user account creation is implemented using the default persistence of Spring-Social-Security where local account
details are stored within the UsersConnectionRepository itself, users are effictively stored as connections to the
"springSocialSecurity" provider.

This local account creation strategy can be overridden and the local accounts can be persisted using your own domain model
by providing custom implementations of a couple of the components
from Spring Social Security - see the forked demo at https://github.com/michaellavelle/spring-social-security-demo for an illustration of this.

Running the demo
----------------

This webapp consists of a basic implementation of Spring Social framework, configured with an in-memory datasource
for persistence of UserConnection data.   This in-memory datasource (configured in spring-config.xml) can be replaced
with custom datasource as necessary.  The PostContruct method in SpringSocialSecurityDemoWebappConfig can be removed if the
in-memory database is replaced.

To get started , clone the spring-social-sparklr-demo project and then execute

mvn jetty:run

from the base directory of the spring-social-security-demo project.

Access http://localhost:8081/ in your web browser.

The application has two primary pages, the public home page ( http://localhost:8081/ ) and a sparklr-protected resource
( http://localhost:8081/protected/sparklr ).    

Spring Security is configured in the spring-config.xml file to treat the protected url as a protected resource and delegates
to spring-social-security for authentication via the springSocialSecurityAuthenticationFilter bean.

Users are then asked to login via spring-social, and once they have authenticated with Sparklr they are redirected back
to the application and locally logged in.

Running the Sparklr application
------------------------------------------

git clone https://github.com/SpringSource/spring-security-oauth.git

cd spring-security-oauth

mvn install -P bootstrap

cd samples/oauth2/sparklr

mvn tomcat:run

Application overview
--------------------

The bulk of this application sets up the environment for Spring Social and Spring Security, with the spring-social-security
bridge between these two frameworks being configured with a few lines of configuration:

```

  <!-- Start Import of Spring Social Security -->

	<!-- Scan classpath for components, including our Social Security Configuration 
		class -->
	<context:component-scan
		base-package="org.socialsignin.springsocial.security" />

   <!-- End Import of Spring Social Security -->
```
```
  <!-- configuration of spring security -->

<!-- Note the springSocialSecurityAuthenticationFilter is registered in place of the FORM_LOGIN_FILTER,
and the entry point for protected resources is defined as the springSocialSecurityEntryPoint -->

	<security:http use-expressions="true"
		entry-point-ref="springSocialSecurityEntryPoint" xmlns="http://www.springframework.org/schema/security">

		<intercept-url pattern="/protected/**" access="hasRole('ROLE_USER')" />
		<intercept-url pattern="/oauthconnect.jsp" access="hasRole('ROLE_USER')" />
		

		<security:logout logout-url="/logout" />

		<anonymous />
		<security:custom-filter position="FORM_LOGIN_FILTER"
			ref="springSocialSecurityAuthenticationFilter" />

	</security:http>
	
	<bean id="springSocialSecurityEntryPoint"
  		class="org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint">
 		<property name="loginFormUrl" value="/oauthlogin.jsp"/>
	</bean>
	

<!-- end configuration of spring security -->

<!-- Configuration of spring social -->

<!-- Note the postSignInUrl is set to /authenticate, the signUp url is set to /signup and a provider specific connect interceptor
is registered for each post-login connect provider -->

	<bean class="org.springframework.social.connect.web.ProviderSignInController">
		<constructor-arg value="${application.secureUrl}" />
		<property name="signUpUrl" value="/signup" />
		<property name="applicationUrl" value="${application.secureUrl}" />
		<property name="postSignInUrl" value="/authenticate" />
		<!-- relies on by-type autowiring for the other constructor-args -->
	</bean>

	<bean class="org.springframework.social.connect.web.ConnectController">
		<!-- relies on by-type autowiring for the constructor-args -->
		<property name="applicationUrl" value="${application.secureUrl}" />
		<property name="interceptors" ref="connectInterceptorList">

	</bean>


<!-- End configuration of spring social -->

```

The only additional code which is needed for this spring-social-security demo is the SparklrConnectInterceptor,
needed because the Spring-Social framework requires API-specific connect interceptors to be registered before
they can be called.  This interceptor is registered with the ConnectController as above.
