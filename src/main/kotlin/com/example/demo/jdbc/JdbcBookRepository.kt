package com.example.demo.jdbc

import com.example.demo.Book
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class JdbcBookRepository(private val jdbcTemplate: JdbcTemplate) {

    private val bookRowMapper: RowMapper<Book> = BeanPropertyRowMapper(Book::class.java)

    fun getByIsbn(isbn: String?): Book? {
        return jdbcTemplate.queryForObject("select * from books where isbn = ?", bookRowMapper, isbn)
    }

    @Transactional
    fun getByIsbnTransactional(isbn: String?): Book? {
        return jdbcTemplate.queryForObject("select * from books where isbn = ?", bookRowMapper, isbn)
    }

    @Transactional(readOnly = true)
    fun getByIsbnTransactionalR(isbn: String?): Book? {
        return jdbcTemplate.queryForObject("select * from books where isbn = ?", bookRowMapper, isbn)
    }
}
