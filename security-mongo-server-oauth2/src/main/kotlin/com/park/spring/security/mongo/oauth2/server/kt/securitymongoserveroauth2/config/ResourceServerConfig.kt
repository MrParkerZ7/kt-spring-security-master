package com.park.spring.security.mongo.oauth2.server.kt.securitymongoserveroauth2.config

import com.park.spring.security.mongo.oauth2.server.kt.securitymongoserveroauth2.service.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer

@Configuration
@EnableResourceServer
class ResourceServerConfig(@Autowired private var authenticationManager: AuthenticationManager? = null,
                           @Autowired private var customUserDetailsService: CustomUserDetailsService? = null) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.parentAuthenticationManager(authenticationManager)
                .userDetailsService(customUserDetailsService)
    }

    override fun configure(http: HttpSecurity?) {
        http!!.requestMatchers()
                .antMatchers("login", "/oauth/authorize")
                .and().authorizeRequests().anyRequest().authenticated()
                .and().formLogin().permitAll()
    }
}