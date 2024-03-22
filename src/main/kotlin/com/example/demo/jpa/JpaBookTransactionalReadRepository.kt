package com.example.demo.jpa

import com.example.demo.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface JpaBookTransactionalReadRepository : JpaRepository<Book, Long> {
    @Transactional(readOnly = true)
    fun findByIsbn(isbn: String): Book?
}
