package com.example.forum.presentation.screens.postdetail

import com.example.forum.domain.model.Comment
import com.example.forum.domain.model.PostWithAuthor

sealed class PostDetailState {
    data object Loading : PostDetailState()
    data class Success(
        val post: PostWithAuthor,
        val comments: List<Comment>
    ) : PostDetailState()
    data class Error(
        val post: PostWithAuthor? = null,
        val message: String
    ) : PostDetailState()
}