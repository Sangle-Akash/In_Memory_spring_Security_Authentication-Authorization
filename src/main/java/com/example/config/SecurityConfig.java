package com.example.config;

//import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig 
{
	   @Bean
	    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
	        // Create in-memory users with roles and encrypted passwords
	        UserDetails admin =User.withUsername("ganapat")
	                .password(passwordEncoder.encode("ganapat@123"))  // Encrypt password
	                .roles("USER","ADMIN")
	                .build();
	        UserDetails user = User.withUsername("Akash")
	                .password(passwordEncoder.encode("Akash@0202"))  // Encrypt password
	                .roles("USER")
	                .build();

	        return new InMemoryUserDetailsManager(user, admin);
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();  // Use BCrypt for secure password encoding
	    }
	    
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	      return http.authorizeHttpRequests(auth -> auth
	    	        .requestMatchers(new AntPathRequestMatcher("/get-all-records/**")).hasAnyRole("ADMIN", "USER") // Allow both ADMIN and USER roles for this endpoint
	    	        .requestMatchers(new AntPathRequestMatcher("/find-by-id/{id}/**")).hasAnyRole("USER") // Allow USER role for this endpoint
	    	        .anyRequest().authenticated()) // All other requests require authentication
	    		   .formLogin(Customizer.withDefaults()) // Enable default form-based login
		    	    .build();
	    	 
 // Enable form-based login
	           
	    }
	    }
	    
