package com.mobility.purchaserequest.security;

import com.mobility.purchaserequest.filters.JwtFilter;
import com.mobility.purchaserequest.redis.JwtRedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable()
				.exceptionHandling().and()
				.authorizeRequests().antMatchers("/api/v1/purchase-request/**").permitAll()
				.anyRequest().authenticated();

	}
}
