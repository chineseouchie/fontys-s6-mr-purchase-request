package com.mobility.purchaserequest.filters;

import com.mobility.purchaserequest.rabbitmq.TokenSender;
import com.mobility.purchaserequest.redis.JwtRedis;
import com.mobility.purchaserequest.utils.JwtParser;


import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@Component
public class JwtFilter extends GenericFilterBean {
	private JwtRedis jwtRedis;

	public JwtFilter(JwtRedis jwtRedis) {
		this.jwtRedis = jwtRedis;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String requestToken = request.getHeader("Authorization");
		System.out.println(requestToken);
		if (requestToken == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		String cachedToken;
		try {
			cachedToken = jwtRedis.get(requestToken);
		} catch (Exception e) {
			cachedToken = null;
		}
		// System.out.println(cachedToken);

		if (cachedToken == null) {
			try {
				String token = TokenSender.auth(requestToken);
				if (!token.equals("")) {
					// System.out.println(token);
					jwtRedis.save(token);
					filterChain.doFilter(servletRequest, servletResponse);
					return;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			if (Long.parseLong(new JwtParser().ParseToken(cachedToken).getExp()) > Instant.now().getEpochSecond()) {
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}
		}
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}
}
