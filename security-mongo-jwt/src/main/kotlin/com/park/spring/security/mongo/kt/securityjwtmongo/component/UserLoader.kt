package com.park.spring.security.mongo.kt.securityjwtmongo.component

import com.park.spring.security.mongo.kt.securityjwtmongo.model.Person
import com.park.spring.security.mongo.kt.securityjwtmongo.model.PersonRepository
import com.park.spring.security.mongo.kt.securityjwtmongo.model.Status
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class UserLoader(@Autowired val personRepository: PersonRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {
        personRepository.deleteAll()

        val people = listOf(
                Person(id = ObjectId(), username = "User", password = "1234", name = "Park", role = listOf("USER"),
                        status = Status(true, true, true, true)),
                Person(id = ObjectId(), username = "Admin", password = "1234", name = "Fuck", role = listOf("ADMIN"),
                        status = Status(true, true, true, true))
        )

        personRepository.saveAll(people)
    }
}