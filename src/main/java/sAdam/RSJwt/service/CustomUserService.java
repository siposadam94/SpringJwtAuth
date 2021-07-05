package sAdam.RSJwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sAdam.RSJwt.entity.User;
import sAdam.RSJwt.repository.UserDetailsRepository;

@Service
public class CustomUserService implements UserDetailsService {

	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	public CustomUserService(UserDetailsRepository userDetailsRepository) {
		this.userDetailsRepository = userDetailsRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userDetailsRepository.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("User not found with Username "+username);
		}
		
		return user;
	}

}
