package com.example.forum.data.local.entity

data class CommentWithAuthor(
    val commentId: Int,
    val commentPostId: Int,
    val commentAuthorName: String,
    val commentAuthorEmail: String,
    val commentBody: String,
)