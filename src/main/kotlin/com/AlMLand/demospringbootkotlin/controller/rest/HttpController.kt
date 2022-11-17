package com.AlMLand.demospringbootkotlin.controller.rest

import com.AlMLand.demospringbootkotlin.domain.Article
import com.AlMLand.demospringbootkotlin.domain.User
import com.AlMLand.demospringbootkotlin.repository.ArticleRepository
import com.AlMLand.demospringbootkotlin.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/users")
class UserController(private val userRepository: UserRepository) {
    @GetMapping
    fun getAllUsers() = ResponseEntity.ok(userRepository.findAll())

    @GetMapping("{login}")
    fun getUserBySlug(@PathVariable login: String): ResponseEntity<User> {
        val user = userRepository.findByLogin(login) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(user)
    }

    @PostMapping
    fun createUser(@RequestBody user: User) =
        ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user))
}

@RestController
@RequestMapping("/api/articles")
class ArticleController(private val articleRepository: ArticleRepository) {
    @GetMapping
    fun getAllArticles() = articleRepository.findAllByOrderByAddedAtDesc()

    @GetMapping("{slug}")
    fun getArticleBySlug(@PathVariable slug: String) = articleRepository.findBySlug(slug)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")

    @PostMapping
    fun createArticle(@RequestBody article: Article) =
        ResponseEntity.status(HttpStatus.CREATED).body(articleRepository.save(article))
}
