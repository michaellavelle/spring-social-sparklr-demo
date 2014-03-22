package org.socialsignin.springsocial.sparklr.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.sparklr.api.Photo;
import org.springframework.social.sparklr.api.Sparklr;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpringSocialSparklrDemoController {
	
	@Autowired(required=false)
	private Sparklr sparklr;

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

	@RequestMapping("/protected/sparklr")
	public String helloProtectedWorld(Map model) {
		model.put("userName", getAuthenticatedUserName());

		// Display on the jsp which security level the page is intended for
		model.put("securityLevel", "Protected");
		
		List<Photo> photos = new ArrayList<Photo>();
		
		photos.addAll(sparklr.meOperations().getPhotos());
		
		
		model.put("photos",photos);
		
		return "helloWorld";
	}
	

}
