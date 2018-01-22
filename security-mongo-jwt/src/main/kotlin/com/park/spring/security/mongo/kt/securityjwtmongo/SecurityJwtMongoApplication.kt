package com.park.spring.security.mongo.kt.securityjwtmongo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SecurityJwtMongoApplication

fun main(args: Array<String>) {
    runApplication<SecurityJwtMongoApplication>(*args)
}
