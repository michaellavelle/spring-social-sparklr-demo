Spring Social Sparklr Demo
===========================

Simple Hello World Webapp demonstrating the <a href="https://github.com/michaellavelle/spring-social-sparklr">
spring-social-sparklr</a> and <a href="https://github.com/socialsignin/spring-social-security">
spring-social-security</a> modules.

Requires version the Sparklr application from spring-security-oauth ( https://github.com/SpringSource/spring-security-oauth ) 
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
with custom datasource as necessary.  The PostContruct method in SpringSocialSparklrDemoWebappConfig can be removed if the
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

