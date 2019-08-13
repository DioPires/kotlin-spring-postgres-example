package com.example.postgresdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class PostgresDemoApplication

fun main(args: Array<String>) {
	runApplication<PostgresDemoApplication>(*args)
}
