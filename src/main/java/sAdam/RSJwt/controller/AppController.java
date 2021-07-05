package sAdam.RSJwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

	@GetMapping("/")
	public String testApp() {
		return "Hello Spring Security";
	}
}
