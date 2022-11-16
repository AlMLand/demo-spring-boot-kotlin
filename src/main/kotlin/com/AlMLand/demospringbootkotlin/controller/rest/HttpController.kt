package com.AlMLand.demospringbootkotlin.controller.rest

import com.AlMLand.demospringbootkotlin.repository.ArticleRepository
import com.AlMLand.demospringbootkotlin.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/users")
class UserController(private val userRepository: UserRepository) {
    @GetMapping
    fun getAllUsers() = userRepository.findAll()
    @GetMapping("{login}")
    fun getUserBySlug(@PathVariable login: String) = userRepository.findByLogin(login)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist")
}

@RestController
@RequestMapping("/api/articles")
class ArticleController(private val articleRepository: ArticleRepository) {
    @GetMapping
    fun getAllArticles() = articleRepository.findAllByOrderByAddedAtDesc()
    @GetMapping("{slug}")
    fun getArticleBySlug(@PathVariable slug: String) = articleRepository.findBySlug(slug)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")
}