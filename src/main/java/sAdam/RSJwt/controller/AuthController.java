package sAdam.RSJwt.controller;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sAdam.RSJwt.config.JWTTokenHelper;
import sAdam.RSJwt.entity.User;
import sAdam.RSJwt.request.AuthenticationRequest;
import sAdam.RSJwt.response.LoginResponse;
import sAdam.RSJwt.response.Userinfo;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class AuthController {
	
	@Autowired
	JWTTokenHelper jwtTokenHelper;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws InvalidKeySpecException, NoSuchAlgorithmException{
		
		final Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
																		  authenticationRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		
		User user = (User)authenticate.getPrincipal();
		String jwtToken = jwtTokenHelper.generateToken(user.getUsername());
		
		LoginResponse response = new LoginResponse();
		response.setToken(jwtToken);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/auth/userinfo")
	public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal UserDetails user){
		User userObj = (User) userDetailsService.loadUserByUsername(user.getUsername());
		Userinfo userInfo = new Userinfo();
		userInfo.setUsername(userObj.getUsername());
		userInfo.setPhoneNumber(userObj.getPhoneNumber());
		userInfo.setRoles(userObj.getAuthorities().toArray());
		
		return ResponseEntity.ok(userInfo);
	}
	

}
