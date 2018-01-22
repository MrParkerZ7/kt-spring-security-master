package com.park.spring.security.mongo.client.oauth.securitymongoclientoauth.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client

@Configuration
@EnableOAuth2Client
class OauthConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http!!.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**").permitAll()
                .antMatchers("/rest/home").authenticated()
                .antMatchers("/rest/user").hasRole("USER")
                .antMatchers("/rest/admin").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
    }
}