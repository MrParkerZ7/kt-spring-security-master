package com.park.spring.security.mongo.kt.securityjwtmongo.controller

import com.park.spring.security.mongo.kt.securityjwtmongo.model.Person
import com.park.spring.security.mongo.kt.securityjwtmongo.model.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/fetch")
class UserController(@Autowired val personRepository: PersonRepository) {

    @GetMapping("/{username}") // for fetch to test
    fun findByUsername(@PathVariable("username") username: String): Person = personRepository.findByUsername(username)

}