package com.park.spring.security.mongo.kt.securitymongo.config

import com.park.spring.security.mongo.kt.securitymongo.model.PersonRepository
import com.park.spring.security.mongo.kt.securitymongo.service.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableMongoRepositories(basePackageClasses = arrayOf(PersonRepository::class))
class SecurityConfig(@Autowired private val customUserDetailsService: CustomUserDetailsService) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity?) {
        http!!.csrf().disable()
        http.authorizeRequests()
                .antMatchers("/home").permitAll()
                .antMatchers("/profile").authenticated()
                .antMatchers("/user").authenticated()
                .antMatchers("/admin").authenticated()
                .and()
                .formLogin()
                //.loginPage("/***.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/rest/profile")
                .failureUrl("/rest/fail")
                .and()
                .logout().logoutSuccessUrl("/rest/home")
                .permitAll()
    }

    private val passwordEncoder: PasswordEncoder
        get() = object : PasswordEncoder {
            override fun encode(charSequence: CharSequence): String {
                return charSequence.toString()
            }

            override fun matches(charSequence: CharSequence, s: String): Boolean {
                return true
            }
        }
}