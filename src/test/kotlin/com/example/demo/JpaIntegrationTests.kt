package com.example.demo

import com.example.demo.jpa.JpaBookRepository
import com.example.demo.jpa.JpaBookTransactionalReadRepository
import com.example.demo.jpa.JpaBookTransactionalRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
class JpaIntegrationTests {
    @Autowired
    lateinit var bookRepository: JpaBookRepository

    @Autowired
    lateinit var bookTransactionalRepo: JpaBookTransactionalRepository

    @Autowired
    lateinit var bookTransactionalReadRepo: JpaBookTransactionalReadRepository

    @Test
    fun test() {
        //additional warmup
        TestUtil.performTest({ bookTransactionalReadRepo.findByIsbn("2") }, 1000)

        val transactionalR = TestUtil.performTest({ bookTransactionalReadRepo.findByIsbn("2") }, 1000)
        val transactional = TestUtil.performTest({ bookTransactionalRepo.findByIsbn("1") }, 1000)
        val transactionalNo = TestUtil.performTest({ bookRepository.findByIsbn("3") }, 1000)

        println("transaction no      : $transactionalNo")
        println("transaction         : $transactional")
        println("transaction readonly: $transactionalR")
    }

    @Test
    fun testWithoutTx() {
        val result = TestUtil.performTest({ bookRepository.findByIsbn("2") }, 1000)
        println("transaction no       : $result")
    }

    @Test
    fun testTx() {
        val result = TestUtil.performTest({ bookTransactionalRepo.findByIsbn("2") }, 1000)
        println("transaction         : $result")
    }

    @Test
    fun testTxReadOnly() {
        val result = TestUtil.performTest({ bookTransactionalReadRepo.findByIsbn("2") }, 1000)
        println("transaction readonly: $result")
    }

    companion object {
        @Container
        @ServiceConnection
        val postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:13.3")
    }
}
