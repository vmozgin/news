package com.example.news.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
													   UserDetailsService userDetailsService,
													   PasswordEncoder passwordEncoder)
			throws Exception {
		var authManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

		authManagerBuilder.userDetailsService(userDetailsService);

		var authProvider = new DaoAuthenticationProvider(passwordEncoder);
		authProvider.setUserDetailsService(userDetailsService);

		authManagerBuilder.authenticationProvider(authProvider);

		return authManagerBuilder.build();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
										   AuthenticationManager authenticationManager)
			throws Exception {
		httpSecurity.authorizeHttpRequests(
						(auth) -> auth.requestMatchers(HttpMethod.POST, "/api/user").permitAll()
								.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
								.anyRequest().authenticated()).csrf(AbstractHttpConfigurer::disable)
				.httpBasic(Customizer.withDefaults()).sessionManagement(
						httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
								SessionCreationPolicy.STATELESS))
				.authenticationManager(authenticationManager);

		return httpSecurity.build();
	}
}
