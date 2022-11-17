package com.AlMLand.demospringbootkotlin

import com.AlMLand.demospringbootkotlin.domain.Article
import com.AlMLand.demospringbootkotlin.domain.User
import com.AlMLand.demospringbootkotlin.repository.ArticleRepository
import com.AlMLand.demospringbootkotlin.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class RepositoriesTest @Autowired constructor(
    val testEntityManager: TestEntityManager,
    val userRepository: UserRepository,
    val articleRepository: ArticleRepository
) {

    @Test
    fun `When findByIdOrNull then return Article`() {
        val juergen = User("springjuergen", "Juergen", "Hoeller")
        testEntityManager.persist(juergen)
        val article = Article(
            "Spring Framework 5.0 goes GA", "Dear Spring community ...",
            "Lorem ipsum", juergen
        )
        testEntityManager.persist(article)
        testEntityManager.flush()

        val found = articleRepository.findByIdOrNull(article.id!!)
        assertThat(found).isEqualTo(article)
    }

    @Test
    fun `When findByLogin then return User`() {
        val juergen = User("springjuergen", "Juergen", "Hoeller")
        testEntityManager.persist(juergen)
        testEntityManager.flush()

        val user = userRepository.findByLogin(juergen.login)
        assertThat(user).isEqualTo(juergen)
    }

}