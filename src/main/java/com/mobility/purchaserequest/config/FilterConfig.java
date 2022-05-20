package com.mobility.purchaserequest.config;

import com.mobility.purchaserequest.filters.IsAdminFilter;
import com.mobility.purchaserequest.filters.IsDealerFilter;
import com.mobility.purchaserequest.filters.IsEmployeeFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
	@Bean
	public FilterRegistrationBean<IsDealerFilter> dealerFilterConfig() {
        FilterRegistrationBean<IsDealerFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new IsDealerFilter());
        registrationBean.setOrder(1);
        registrationBean.addUrlPatterns(
            // "/api/v1/purchase-request",
            // "/api/v1/purchase-request/*",
            "/api/v1/purchase-request/*/accept",
            "/api/v1/purchase-request/*/decline"
            // "/api/v1/purchase-request/*/decline"
        );
        return registrationBean;
    }

    @Bean
	public FilterRegistrationBean<IsEmployeeFilter> employeeFilterConfig() {
        FilterRegistrationBean<IsEmployeeFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new IsEmployeeFilter());
        registrationBean.setOrder(2);
        registrationBean.addUrlPatterns(
            "/api/v1/purchase-request",
            // "/api/v1/purchase-request/*",
            "/api/v1/purchase-request/*/accept",
            "/api/v1/purchase-request/*/decline"
            // "/api/v1/purchase-request/*/decline"
        );
        return registrationBean;
    }

	@Bean
	public FilterRegistrationBean<IsAdminFilter> adminFilterConfig() {
        FilterRegistrationBean<IsAdminFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new IsAdminFilter());
        registrationBean.setOrder(3);
        registrationBean.addUrlPatterns(
            "/api/v1/purchase-request",
            "/api/v1/purchase-request/create",
            "/api/v1/purchase-request/*",
            "/api/v1/purchase-request/dealers"
        );
        return registrationBean;
    }
}
