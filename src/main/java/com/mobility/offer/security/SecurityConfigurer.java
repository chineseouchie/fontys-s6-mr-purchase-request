package com.mobility.offer.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.exceptionHandling().and()
			.authorizeRequests().antMatchers("/api/v1/purchase/request/**").permitAll()
			.anyRequest().authenticated();
    }
}