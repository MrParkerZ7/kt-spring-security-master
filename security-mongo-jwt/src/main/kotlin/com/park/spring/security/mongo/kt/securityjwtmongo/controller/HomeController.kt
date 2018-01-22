package com.park.spring.security.mongo.kt.securityjwtmongo.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.PermitAll

@RestController
@RequestMapping
class HomeController {

    @PermitAll
    @GetMapping("/home")
    fun home(): String = "Home!!"

}

@RestController
@RequestMapping("/secure")
class SecuredController {

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/profile")
    fun profile(): String = "Profile Secured!!"

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    fun user(): String = "User Secured!!"

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    fun admin(): String = "Admin Secured!!"
}

