package com.AlMLand.demospringbootkotlin

import com.AlMLand.demospringbootkotlin.domain.Article
import com.AlMLand.demospringbootkotlin.domain.User
import com.AlMLand.demospringbootkotlin.repository.ArticleRepository
import com.AlMLand.demospringbootkotlin.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest
class HttpControllerTest(@Autowired val mockMvc: MockMvc, @Autowired val objectMapper: ObjectMapper) {
    @MockkBean
    private lateinit var userRepository: UserRepository

    @MockkBean
    private lateinit var articleRepository: ArticleRepository

    @Test
    fun `Create user`() {
        val user = User("am", "alex", "m_land")

        every { userRepository.save(any()) } returns user

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/users")
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("login").value(user.login))
            .andExpect(MockMvcResultMatchers.jsonPath("firstName").value(user.firstName))
            .andExpect(MockMvcResultMatchers.jsonPath("lastName").value(user.lastName))
    }

    @Test
    fun `Get user by login, user not founded`() {
        every { userRepository.findByLogin(any()) } returns null

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/users/{login}", "test")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `List users`() {
        val alex = User("am", "alex", "m_land")
        val timur = User("tm", "timur", "m_land")

        every { userRepository.findAll() } returns listOf(alex, timur)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/users")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("[0].login").value(alex.login))
            .andExpect(MockMvcResultMatchers.jsonPath("[1].login").value(timur.login))
    }

    @Test
    fun `Create article`() {
        val user = User("testLogin", "testFirstName", "testLastName")
        val article = Article("title1", "headline1", "content1", user)

        every { articleRepository.save(any()) } returns article

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/articles")
                .content(objectMapper.writeValueAsString(article))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("author.login").value(user.login))
            .andExpect(MockMvcResultMatchers.jsonPath("slug").value(article.slug))
    }

    @Test
    fun `List articles`() {
        val user = User("testLogin", "testFirstName", "testLastName")
        val article1 = Article("title1", "headline1", "content1", user)
        val article2 = Article("title2", "headline2", "content2", user)

        every { articleRepository.findAllByOrderByAddedAtDesc() } returns listOf(article1, article2)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/articles")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[0].author.login").value(user.login))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[0].slug").value(article1.slug))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[1].author.login").value(user.login))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[1].slug").value(article2.slug))
    }
}
