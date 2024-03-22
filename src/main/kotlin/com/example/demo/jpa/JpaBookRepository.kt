package com.example.demo.jpa

import com.example.demo.Book
import org.springframework.data.jpa.repository.JpaRepository

interface JpaBookRepository : JpaRepository<Book, Long> {
    fun findByIsbn(isbn: String): Book?
}
