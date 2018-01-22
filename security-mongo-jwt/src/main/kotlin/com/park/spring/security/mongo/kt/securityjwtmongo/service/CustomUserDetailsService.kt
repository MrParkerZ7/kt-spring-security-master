package com.park.spring.security.mongo.kt.securityjwtmongo.service

import com.park.spring.security.mongo.kt.securityjwtmongo.model.CustomPersonDetails
import com.park.spring.security.mongo.kt.securityjwtmongo.model.Person
import com.park.spring.security.mongo.kt.securityjwtmongo.model.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomUserDetailsService(@Autowired var personRepository: PersonRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        var optionalPerson: Optional<Person> = Optional.ofNullable(personRepository.findByUsername(username))
        optionalPerson.orElseThrow { (RuntimeException("Username not found!!")) }
        return optionalPerson
                .map<CustomPersonDetails>({ CustomPersonDetails(optionalPerson.get()) }).get()

    }

}