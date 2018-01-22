package com.park.spring.security.mongo.client.oauth.securitymongoclientoauth.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest")
class HomeController {

    @GetMapping("/home")
    fun home(): String = "Home Client!!"

    @GetMapping("/user")
    fun user(): String = "User Client!!"

    @GetMapping("/admin")
    fun admin(): String = "Admin Client!!"
}