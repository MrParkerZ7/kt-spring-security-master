package com.park.spring.security.kt.securitybasic.config

import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.inMemoryAuthentication()
                .passwordEncoder(NoOpPasswordEncoder.getInstance()) // Not Secured Deprecated
                .withUser("User").password("1234").roles("USER").and()
                .withUser("Admin").password("1234").roles("ADMIN")

    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity?) {
        http!!.authorizeRequests()
                .antMatchers("/home").permitAll()
                .antMatchers("/profile").authenticated()
                .antMatchers("/user").authenticated()
                .antMatchers("/admin").authenticated()
                .and().formLogin()

    }
}