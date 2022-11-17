package com.AlMLand.demospringbootkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class DemoSpringBootKotlinApplication

fun main(args: Array<String>) {
    runApplication<DemoSpringBootKotlinApplication>(*args)
}
