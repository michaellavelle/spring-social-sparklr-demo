package org.socialsignin.springsocial.security.demo;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpringSocialSecurityDemoController {

	private String getAuthenticatedUserName() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return authentication == null ? null : authentication.getName();
	}

	@RequestMapping("/")
	public String helloPublicWorld(Map model) {
		model.put("userName", getAuthenticatedUserName());

		// Display on the jsp which security level the page is intended for
		model.put("securityLevel", "Public");

		return "helloWorld";
	}

	@RequestMapping("/protected")
	public String helloProtectedWorld(Map model) {
		model.put("userName", getAuthenticatedUserName());

		// Display on the jsp which security level the page is intended for
		model.put("securityLevel", "Protected");

		return "helloWorld";
	}
	
	@RequestMapping("/protected/twitter")
	public String helloTwitterProtectedWorld(Map model) {
		model.put("userName", getAuthenticatedUserName());

		// Display on the jsp which security level the page is intended for
		model.put("securityLevel", "Twitter Protected");

		return "helloWorld";
	}
	
	@RequestMapping("/protected/facebook")
	public String helloFacebookProtectedWorld(Map model) {
		model.put("userName", getAuthenticatedUserName());

		// Display on the jsp which security level the page is intended for
		model.put("securityLevel", "Facebook Protected");

		return "helloWorld";
	}
	
	@RequestMapping("/protected/facebookTwitter")
	public String helloFacebookAndTwitterProtectedWorld(Map model) {
		model.put("userName", getAuthenticatedUserName());

		// Display on the jsp which security level the page is intended for
		model.put("securityLevel", "Facebook and Twitter Protected");

		return "helloWorld";
	}
	
	

}
