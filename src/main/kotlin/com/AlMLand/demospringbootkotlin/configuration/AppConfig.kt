package com.AlMLand.demospringbootkotlin.configuration

import com.AlMLand.demospringbootkotlin.domain.Article
import com.AlMLand.demospringbootkotlin.domain.User
import com.AlMLand.demospringbootkotlin.repository.ArticleRepository
import com.AlMLand.demospringbootkotlin.repository.UserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    @Bean
    fun dbInit(userRepository: UserRepository, articleRepository: ArticleRepository) = ApplicationRunner {
        val smaldini = userRepository.save(User(
            login = "smaldini",
            firstName = "Stéphane",
            lastName = "Maldini"))
        articleRepository.save(Article(
            title = "Reactor Bismuth is out",
            headLine = "Lorem ipsum",
            content = "dolor sit amet",
            author = smaldini
        ))
        articleRepository.save(Article(
            title = "Reactor Aluminium has landed",
            headLine = "Lorem ipsum",
            content = "dolor sit amet",
            author = smaldini
        ))
    }
}