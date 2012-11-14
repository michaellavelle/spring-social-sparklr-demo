package org.socialsignin.springsocial.sparklr.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.sparklr.api.Photo;
import org.springframework.social.sparklr.api.Sparklr;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpringSocialSparklrDemoController {
	
	@Autowired
	private UsersConnectionRepository usersConnectionRepository;

	private String getAuthenticatedUserName() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return authentication == null ? null : authentication.getName();
	}
	
	private List<Sparklr> getAuthenticatedSparklrs()
	{
		List<Sparklr> authenticatedSparklrs = new ArrayList<Sparklr>();
		String authenticatedUserName = getAuthenticatedUserName();
		if (authenticatedUserName != null)
		{
			List<Connection<Sparklr>> sparklrConnections = usersConnectionRepository.createConnectionRepository(authenticatedUserName).findConnections(Sparklr.class);
			for (Connection<Sparklr> connection : sparklrConnections)
			{
				authenticatedSparklrs.add(connection.getApi());
			}
		}
		return authenticatedSparklrs;
		
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
		
		for (Sparklr sparklr : getAuthenticatedSparklrs())
		{
			photos.addAll(sparklr.meOperations().getPhotos());
		}
		
		model.put("photos",photos);
		
		return "helloWorld";
	}
	

}
