package com.AlMLand.demospringbootkotlin.controller

import com.AlMLand.demospringbootkotlin.domain.Article
import com.AlMLand.demospringbootkotlin.domain.User
import com.AlMLand.demospringbootkotlin.format
import com.AlMLand.demospringbootkotlin.repository.ArticleRepository
import com.AlMLand.demospringbootkotlin.toSlug
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import javax.persistence.ManyToOne

@Controller
class HtmlController(private val articleRepository: ArticleRepository) {

    @GetMapping("/")
    fun blog(model: Model): String {
        //model.addAttribute("title", "Custom title")
        model["title"] = "Custom title"
        model["articles"] = articleRepository.findAllByOrderByAddedAtDesc().map { it.render() }
        return "blog"
    }

    @GetMapping("/article/{slug}")
    fun article(@PathVariable slug: String, model: Model): String {
        val article = articleRepository.findBySlug(slug)
            ?.render()
            ?:throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")
        model["title"] = article.title
        model["article"] = article
        return "article"
    }

}

private fun Article.render() = RenderArticle(
    slug,
    title,
    headLine,
    content,
    author,
    addedAt.format()
)

data class RenderArticle (
    val slug: String,
    val title: String,
    val headLine: String,
    val content: String,
    val author: User,
    val addedAt: String
    )
