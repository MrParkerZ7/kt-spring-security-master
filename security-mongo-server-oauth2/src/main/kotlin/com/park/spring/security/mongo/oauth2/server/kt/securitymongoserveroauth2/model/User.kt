package com.park.spring.security.mongo.oauth2.server.kt.securitymongoserveroauth2.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import kotlin.streams.toList

@Document
data class Person(@Id var id: ObjectId? = null, @Indexed(unique = true) var username: String? = null,
                  var password: String? = null, @Indexed var name: String? = null,
                  var role: Collection<String>? = null, var status: Status? = null)

data class Status(var isAccountNonExpired: Boolean, var isAccountNonLocked: Boolean,
                  var isCredentialsNonExpired: Boolean, var isEnable: Boolean)

class CustomPersonDetails(private var person: Person) : UserDetails {

    override fun getAuthorities(): List<SimpleGrantedAuthority> = person.role!!.stream()
            .map { role -> SimpleGrantedAuthority("ROLE_" + role) }.toList()

    override fun getUsername(): String = person.username!!

    override fun getPassword(): String = person.password!!

    override fun isEnabled(): Boolean = person.status!!.isEnable

    override fun isCredentialsNonExpired(): Boolean = person.status!!.isCredentialsNonExpired

    override fun isAccountNonExpired(): Boolean = person.status!!.isAccountNonExpired

    override fun isAccountNonLocked(): Boolean = person.status!!.isAccountNonLocked
}

@Repository
interface PersonRepository : MongoRepository<Person, ObjectId> {
    fun findByUsername(username: String?): Person
}