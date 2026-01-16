package com.springboot.car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration 
public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.csrf((csrf) -> csrf.disable()) 
		.authorizeHttpRequests(authorize -> authorize
				
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.requestMatchers("/api/user/signup").permitAll()
				.requestMatchers("/api/user/token").authenticated()
				.requestMatchers("/api/user/details").authenticated()
				.requestMatchers("/api/upload/profile-pic").hasAnyAuthority("MANAGER")
				.requestMatchers("/api/car/upload/car-pic").hasAnyAuthority("MANAGER","CAROWNER")
				.requestMatchers("/api/customer/upload/profile-pic").hasAnyAuthority("CUSTOMER")
				.requestMatchers("/api/carowner/upload/profile-pic").hasAnyAuthority("CAROWNER")
				
				.requestMatchers("/api/car/all").permitAll()
				.requestMatchers("/api/car/add").hasAnyAuthority("CAROWNER")
				.requestMatchers("/api/car/search/price").permitAll()				
				.requestMatchers("/api/car/available").permitAll()
				.requestMatchers("/api/car//search/price-and-availability").permitAll()
				.requestMatchers("/api/car/update/{id}").hasAnyAuthority("CAROWNER","MANAGER")
				.requestMatchers("/api/car/{id}").hasAnyAuthority("CAROWNER","MANAGER","CUSTOMER")
				.requestMatchers("/api/car/by-owner/{ownerId}").hasAnyAuthority("MANAGER")
				.requestMatchers("/api/car/by-owner").hasAnyAuthority("CAROWNER")
				.requestMatchers("/api/car/delete/{id}").hasAnyAuthority("CAROWNER")
				
				
				.requestMatchers("/api/customer/add").permitAll()
				.requestMatchers("/api/customer/update").hasAuthority("CUSTOMER")
				.requestMatchers("/api/customer/get-one").hasAuthority("CUSTOMER")
				.requestMatchers("/api/customer/delete").hasAuthority("CUSTOMER")
				.requestMatchers("/api/customer/get-all").hasAnyAuthority("MANAGER")

				
				
				.requestMatchers("/api/carowner/get-all").hasAnyAuthority("MANAGER")				
				.requestMatchers("/api/carowner/add").permitAll()
				.requestMatchers("/api/carowner/update").hasAuthority("CAROWNER")
				.requestMatchers("/api/carowner/get-one").hasAuthority("CAROWNER")
				.requestMatchers("/api/carowner/delete").hasAuthority("CAROWNER")

				
				
				
				.requestMatchers("/api/customer/book/car/{carId}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/book/get-car/{carId}").hasAnyAuthority("CAROWNER")
				.requestMatchers("/api/customer/booking/update/{bookingId}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/customer/booking/cancel/{bookingId}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/customer/view/{bookingId}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/manager/view-all-bookings").hasAuthority("MANAGER")
				.requestMatchers("/api/by-customer").hasAnyAuthority("CUSTOMER")
				.requestMatchers("/api/by-customer/{customerId}").hasAnyAuthority("MANAGER")
				.requestMatchers("/api/booking/un-paid").hasAnyAuthority("MANAGER")
				.requestMatchers("/api/customer/payment/{bookingId}").hasAuthority("CUSTOMER")
				

				
				.requestMatchers("/api/maintenance/add").hasAnyAuthority("MANAGER")
		        .requestMatchers("/api/maintenance/all").hasAnyAuthority("MANAGER")
   	        .requestMatchers("/api/maintenance/update/{maintenanceId}").hasAnyAuthority("MANAGER")
				
		        
		        
		        .requestMatchers("/api/returnlog/add").hasAnyAuthority("MANAGER")
		        .requestMatchers("/api/returnlog/all").hasAnyAuthority("MANAGER")
		        .requestMatchers("/api/returnlog/update/{id}").hasAnyAuthority("MANAGER")
		        .requestMatchers("/api/returnlog/delete/{id}").hasAnyAuthority("MANAGER")

		        
		        
		        .requestMatchers("/api/ownerpayout/create/{bookingId}").hasAuthority("MANAGER")

		        
				.requestMatchers("/api/review/add/{carId}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/review/update/{id}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/review/car/{carId}").permitAll()
				.requestMatchers("/api/review/delete/{reviewId}").hasAnyAuthority("CUSTOMER")
				

				
				
				.requestMatchers("/api/manager/add").permitAll()
				.requestMatchers("/api/manager/update").hasAnyAuthority("MANAGER")
				.requestMatchers("/api/manager/view").hasAnyAuthority("MANAGER")
				.requestMatchers("/api/manager/delete").hasAnyAuthority("MANAGER")
				.requestMatchers("/api/manager/booking/stats").hasAnyAuthority("MANAGER")
				
				.anyRequest().authenticated() 
				)
		 .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
		 .httpBasic(Customizer.withDefaults()); 
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {  
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager getAuthManager(AuthenticationConfiguration auth) 
			throws Exception {
		  return auth.getAuthenticationManager();
	 }
}







//.requestMatchers("/api/customer/search").hasAnyAuthority("MANAGER")
//.requestMatchers("/api/car/filter").permitAll()
//.requestMatchers("/api/carowner/search").hasAnyAuthority("MANAGER")	
//.requestMatchers("/api/customer/book/get-car/{carId}").hasAnyAuthority("MANAGER","CUSTOMER")
//.requestMatchers("/api/car/book/customer").hasAnyAuthority("CUSTOMER")
//.requestMatchers("/api/customer/recent-booking/{carId}").hasAuthority("CUSTOMER")
//.requestMatchers("/api/payment/add").hasAuthority("CUSTOMER")
//.requestMatchers("/api/payment/update/{paymentId}").hasAuthority("CUSTOMER")
//.requestMatchers("/api/payment/cancel/{paymentId}").hasAuthority("CUSTOMER")
//.requestMatchers("/api/payment/view/{paymentId}").hasAnyAuthority("CUSTOMER", "MANAGER")
//.requestMatchers("/api/payment/get-all").hasAnyAuthority("MANAGER")
//.requestMatchers("/api/payment/customer/{customerId}").hasAnyAuthority("MANAGER")
//.requestMatchers("/api/payment/booking/{bookingId}").hasAnyAuthority("MANAGER")
//.requestMatchers("/api/maintenance/car/{carId}").hasAnyAuthority("MANAGER")				
//.requestMatchers("/api/returnlog/fuelLevel/{fuelLevel}").hasAuthority("MANAGER")
//.requestMatchers("/api/ownerpayout/all").hasAuthority("MANAGER")
//.requestMatchers("/api/ownerpayout/owner").hasAuthority("CAROWNER")
//.requestMatchers("/api/review/rating").hasAnyAuthority("MANAGER","CUSTOMER")
//.requestMatchers("/api/review/customer").hasAuthority("CUSTOMER")