package com.park.spring.security.mongo.kt.securityjwtmongo.security

import com.park.spring.security.mongo.kt.securityjwtmongo.model.CustomPersonDetails
import com.park.spring.security.mongo.kt.securityjwtmongo.model.JwtAuthenticationToken
import com.park.spring.security.mongo.kt.securityjwtmongo.model.Person
import com.park.spring.security.mongo.kt.securityjwtmongo.model.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.stereotype.Component
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(httpServletRequest: HttpServletRequest?, httpServletResponse: HttpServletResponse?,
                          authenticationException: AuthenticationException?) {
        httpServletResponse!!.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED")
    }
}

@Component
class JwtAuthenticationProvider(@Autowired var validator: JwtValidator,
                                @Autowired var personRepository: PersonRepository) : AbstractUserDetailsAuthenticationProvider() {
    override fun additionalAuthenticationChecks(userDetails: UserDetails?, authentication: UsernamePasswordAuthenticationToken?) {}
    override fun supports(authentication: Class<*>?): Boolean = JwtAuthenticationToken::class.java.isAssignableFrom(authentication)
    override fun retrieveUser(username: String?, authentication: UsernamePasswordAuthenticationToken?): UserDetails {
        val jwtAuthenticationToken: JwtAuthenticationToken = authentication as JwtAuthenticationToken
        val token: String = jwtAuthenticationToken.token

        val personCli: Person? = validator.validate(token)
        val personDB = personRepository.findByUsername(personCli!!.username)

        if (!personCli.username.equals(personDB.username) || !personCli.password.equals(personDB.password))
            throw RuntimeException("JWT Token is incorrect")
        else
            return CustomPersonDetails(personDB)
    }
}

class JwtAuthenticationTokenFilter : AbstractAuthenticationProcessingFilter("/secure/**") {
    override fun attemptAuthentication(httpServletRequest: HttpServletRequest?, httpServletResponse: HttpServletResponse?): Authentication {
        val header: String = httpServletRequest!!.getHeader("Authorize")
        if (header == null || !header.startsWith("Token ")) throw RuntimeException("JWT Token is missing")
        var authenticationToken: String = header.substring(6)
        var token: JwtAuthenticationToken = JwtAuthenticationToken(authenticationToken)
        return authenticationManager.authenticate(token)
    }

    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, authResult: Authentication?) {
        super.successfulAuthentication(request, response, chain, authResult)
        chain!!.doFilter(request, response)
    }
}