package com.example.demo

import com.example.demo.TestUtil.performTest
import com.example.demo.jdbc.JdbcBookRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*

@SpringBootTest
@Testcontainers
class JdbcIntegrationTests {

    @Autowired
    lateinit var bookRepository: JdbcBookRepository

    @Test
    fun test() {
        val transactional: LongSummaryStatistics = performTest({ bookRepository.getByIsbnTransactional("1") }, 1000)
        val transactionalR: LongSummaryStatistics = performTest({ bookRepository.getByIsbnTransactionalR("2") }, 1000)
        val transactionalNo: LongSummaryStatistics = performTest({ bookRepository.getByIsbn("3") }, 1000)

        println("transaction no      : $transactionalNo")
        println("transaction         : $transactional")
        println("transaction readonly: $transactionalR")
    }

    companion object {
        @Container
        @ServiceConnection
        val postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:13.3")
    }
}

