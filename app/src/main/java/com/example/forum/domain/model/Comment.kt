package com.example.forum.domain.model

data class Comment (
    val id: Int,
    val postId: Int,
    val authorName: String,
    val authorEmail: String,
    val body: String,
)