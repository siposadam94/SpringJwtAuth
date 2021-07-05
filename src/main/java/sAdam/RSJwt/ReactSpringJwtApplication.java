package sAdam.RSJwt;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import sAdam.RSJwt.entity.Authority;
import sAdam.RSJwt.entity.User;
import sAdam.RSJwt.repository.UserDetailsRepository;

@SpringBootApplication
public class ReactSpringJwtApplication {
	
	private PasswordEncoder passwordEncoder;
	private UserDetailsRepository userDetailsRepository;
	
	@Autowired
	public ReactSpringJwtApplication(PasswordEncoder passwordEncoder, UserDetailsRepository userDetailsRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userDetailsRepository = userDetailsRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(ReactSpringJwtApplication.class, args);
	}
	
	@PostConstruct
	protected void init() {
		List<Authority> authorityList = new ArrayList<>();
		authorityList.add(new Authority("USER","User role"));
		authorityList.add(new Authority("ADMIN","Admin role"));
		
		User user = new User("adam");
		user.setPassword(passwordEncoder.encode("adamka"));
		user.setAuthorities(authorityList);
		user.setPhoneNumber("079080701");
		
		userDetailsRepository.save(user);
	
	}

}
