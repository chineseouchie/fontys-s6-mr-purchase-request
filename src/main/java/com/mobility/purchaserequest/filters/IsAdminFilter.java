package com.mobility.purchaserequest.filters;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobility.purchaserequest.models.Jwt;
import com.mobility.purchaserequest.utils.JwtParser;

import java.io.IOException;
import java.util.List;

@Component
public class IsAdminFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String requestToken = request.getHeader("Authorization");
		Jwt jwt = new JwtParser().ParseToken(requestToken);
		List<String> roles = jwt.getRoles();

		boolean allowed = roles.contains("Admin") || roles.contains("Employee");
		
		if (allowed) {
			filterChain.doFilter(servletRequest, servletResponse);
		}
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}
	
	
}
