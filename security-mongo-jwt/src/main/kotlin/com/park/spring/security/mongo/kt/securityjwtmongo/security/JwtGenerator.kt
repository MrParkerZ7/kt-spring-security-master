package com.park.spring.security.mongo.kt.securityjwtmongo.security

import com.park.spring.security.mongo.kt.securityjwtmongo.model.Person
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component

@Component
class JwtGenerator {

    fun genrate(person: Person): String {
        var claims: Claims = Jwts.claims().setSubject(person.username)
        claims.put("password", person.password)

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS384, "ParkZ7")
                .compact()
    }
}