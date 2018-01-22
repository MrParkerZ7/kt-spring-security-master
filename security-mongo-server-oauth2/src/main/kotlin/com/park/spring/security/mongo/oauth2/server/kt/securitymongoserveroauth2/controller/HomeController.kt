package com.park.spring.security.mongo.oauth2.server.kt.securitymongoserveroauth2.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/rest/home")
class HomeController {
    @GetMapping("/principal")
    fun principal(principal: Principal): Principal = principal

    @GetMapping
    fun home(): String = "Home Server!! Oauth2"
}