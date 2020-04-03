package pl.ziemniakoss.studentsresourcesmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class ClassesController {
	@Qualifier("customUserDetailsService")
	@Autowired
	private UserDetailsService userDetailsService;

	@GetMapping("/hek")
	@RolesAllowed({"ADMIN"})
	public String hek(){
		return "Hello";
	}
}
