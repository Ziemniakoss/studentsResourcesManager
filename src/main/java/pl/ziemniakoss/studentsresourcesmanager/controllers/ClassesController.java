package pl.ziemniakoss.studentsresourcesmanager.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ClassesController {
	@GetMapping("/hek")
	public String hek(){
		return "Hello";
	}
}
