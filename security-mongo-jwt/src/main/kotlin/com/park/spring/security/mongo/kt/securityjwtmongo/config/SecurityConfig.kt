package com.park.spring.security.mongo.kt.securityjwtmongo.config

import com.park.spring.security.mongo.kt.securityjwtmongo.security.JwtAuthenticationEntryPoint
import com.park.spring.security.mongo.kt.securityjwtmongo.security.JwtAuthenticationProvider
import com.park.spring.security.mongo.kt.securityjwtmongo.security.JwtAuthenticationTokenFilter
import com.park.spring.security.mongo.kt.securityjwtmongo.security.JwtSuccessHandler
import com.park.spring.security.mongo.kt.securityjwtmongo.service.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(@Autowired private var jwtAuthenticationProvider: JwtAuthenticationProvider,
                     @Autowired private var entryPoint: JwtAuthenticationEntryPoint,
                     @Autowired private var userDetailsService: CustomUserDetailsService) : WebSecurityConfigurerAdapter() {

    @Bean
    override fun authenticationManager(): AuthenticationManager = ProviderManager(listOf<AuthenticationProvider>(jwtAuthenticationProvider))

    @Bean
    fun authenticationTokenFilter(): JwtAuthenticationTokenFilter {
        var filter = JwtAuthenticationTokenFilter()
        filter.setAuthenticationManager(authenticationManager())
        filter.setAuthenticationSuccessHandler(JwtSuccessHandler())
        return filter
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncounder())
    }

    override fun configure(http: HttpSecurity?) {
        http!!.csrf().disable()
        http.authorizeRequests()
                .antMatchers("/fetch/**").permitAll()
                .antMatchers("/token").permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers("/secure/profile").authenticated()
                .antMatchers("/secure/user").authenticated()
                .antMatchers("/secure/admin").authenticated()
                .and().formLogin().permitAll()
                .and().exceptionHandling().authenticationEntryPoint(entryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
        http.headers().cacheControl()
    }

    private fun getPasswordEncounder(): PasswordEncoder {
        return object : PasswordEncoder {
            override fun encode(charSequence: CharSequence?): String = charSequence as String
            override fun matches(charSequence: CharSequence?, encodedPassword: String?): Boolean = true
        }
    }
}