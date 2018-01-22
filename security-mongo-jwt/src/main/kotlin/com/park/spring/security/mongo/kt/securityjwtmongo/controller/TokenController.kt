package com.park.spring.security.mongo.kt.securityjwtmongo.controller

import com.park.spring.security.mongo.kt.securityjwtmongo.model.Person
import com.park.spring.security.mongo.kt.securityjwtmongo.security.JwtGenerator
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/token")
class TokenController(private var jwtGenerator: JwtGenerator) {

    @PostMapping
    fun generate(@RequestBody person: Person): String = jwtGenerator.genrate(person)
}