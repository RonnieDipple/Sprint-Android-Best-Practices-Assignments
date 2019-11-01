package com.example.dagger2assignment.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    val userId: Int,
    @field:PrimaryKey
    val id: Int,
    val title: String,
    val body: String
)