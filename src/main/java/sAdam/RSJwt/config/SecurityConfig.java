package sAdam.RSJwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import sAdam.RSJwt.service.CustomUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	CustomUserService userService;
	JWTTokenHelper jwtTokenHelper;
	AuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	public SecurityConfig(CustomUserService userService,JWTTokenHelper jwtTokenHelper,AuthenticationEntryPoint authenticationEntryPoint) {
		this.userService = userService;
		this.jwtTokenHelper = jwtTokenHelper;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//In-memory
		//auth.inMemoryAuthentication().withUser("adam").password(passwordEncoder().encode("adamka")).authorities("USER","ADMIN");
		//dbs
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
		//dao
		//auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
		.and()
			.cors()
		.and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/h2-console/**","/api/v1/auth/login").permitAll()
			.antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
			.anyRequest().authenticated()
		.and()
			.addFilterBefore(new JWTAuthenticationFilter(userService, jwtTokenHelper),UsernamePasswordAuthenticationFilter.class);

//		http
//		.authorizeRequests()
//		.antMatchers("/h2-console/**").permitAll()
//		.anyRequest().authenticated()//.permitAll();
		//.and()
		//.formLogin()
		//.httpBasic();
		//.and()
		
	}

	
//	@Bean
//	DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//		daoAuthenticationProvider.setUserDetailsService(userService);
//		return daoAuthenticationProvider;
//	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
}
