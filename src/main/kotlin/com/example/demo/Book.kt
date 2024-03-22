package com.example.demo

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "books")
class Book {
    @Id
    var id: Int = 0

    @Column
    var name: String? = null

    @Column
    var isbn: String? = null
}