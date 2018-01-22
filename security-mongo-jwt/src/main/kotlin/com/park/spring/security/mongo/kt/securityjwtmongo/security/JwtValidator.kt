package com.park.spring.security.mongo.kt.securityjwtmongo.security

import com.park.spring.security.mongo.kt.securityjwtmongo.model.Person
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component

@Component
class JwtValidator {
    private var secret = "ParkZ7"

    fun validate(token: String): Person? {
        var person: Person? = null

        try {
            var body: Claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .body

            person = Person()
            person.username = body.subject
            person.password = body.get("password") as String? // .toString()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return person
    }
}