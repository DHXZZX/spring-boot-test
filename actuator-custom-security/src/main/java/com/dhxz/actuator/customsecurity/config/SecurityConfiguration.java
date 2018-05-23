package com.dhxz.actuator.customsecurity.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.web.mappings.MappingsEndpoint;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {

        UserDetails user = User.builder().username("user").password("password").authorities("ROLE_USER").build();
        UserDetails admin = User.builder().username("admin").password("admin").authorities("ROLE_ACTUATOR","ROLE_USER").build();
        return new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder().username("user").password("password")
                        .authorities("ROLE_USER").build(),
                User.withDefaultPasswordEncoder().username("admin").password("admin")
                        .authorities("ROLE_ACTUATOR", "ROLE_USER").build());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .requestMatchers(EndpointRequest.to("health","info")).permitAll()
                .requestMatchers(EndpointRequest.toAnyEndpoint().excluding(MappingsEndpoint.class)).hasRole("ACTUATOR")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/foo").permitAll()
                .antMatchers("/**").hasRole("USER")
                .and()
                .cors()
                .and()
                .httpBasic();

    }
}
