package com.park.spring.security.kt.securitybasic.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class HomeController {

    @GetMapping("/home")
    fun home(): String = "Home!!"

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/profile")
    fun profile(): String = "Profile Secured!!"

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/user")
    fun user(): String = "User Secured!!"

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/admin")
    fun admin(): String = "Admin Secured!!"
}