package com.example.forum.presentation.screens.posts

import com.example.forum.domain.model.PostWithAuthor

sealed class PostsState {
    data object Loading : PostsState()
    data class Success(val posts: List<PostWithAuthor>) : PostsState()
    data class Error(val message: String) : PostsState()
}