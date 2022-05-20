package com.mobility.purchaserequest.config;

import com.mobility.purchaserequest.filters.IsAdminFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<IsAdminFilter> adminFilter() {
        FilterRegistrationBean<IsAdminFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new IsAdminFilter());
        registrationBean.addUrlPatterns("/api/v1/purchase-request");
        return registrationBean;
    }
}
