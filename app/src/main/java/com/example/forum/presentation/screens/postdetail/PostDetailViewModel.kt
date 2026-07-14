package com.example.forum.presentation.screens.postdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.forum.domain.model.PostWithAuthor
import com.example.forum.domain.usecase.GetCommentsForPostUseCase
import com.example.forum.domain.usecase.GetPostsWithAuthorsUseCase
import com.example.forum.data.repository.CommentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.isActive

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsWithAuthorsUseCase,
    private val getCommentsUseCase: GetCommentsForPostUseCase,
    private val commentRepository: CommentRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val postId: Int = savedStateHandle["postId"] ?: 0
    private val _uiState = MutableStateFlow<PostDetailState>(PostDetailState.Loading)
    val uiState: StateFlow<PostDetailState> = _uiState.asStateFlow()

    init {
        loadPostDetail()
    }

    fun loadPostDetail() {
        viewModelScope.launch {
            _uiState.value = PostDetailState.Loading
            try {
                try {
                    commentRepository.refreshCommentsForPost(postId)
                } catch (e: Exception) {
                }

                getPostsUseCase()
                    .catch { error ->
                        _uiState.value = PostDetailState.Error(
                            message = error.message ?: "error"
                        )
                    }
                    .collect { posts ->
                        val post = posts.find { it.post.id == postId }
                        if (post != null) {
                            loadComments(post)
                        } else {
                            _uiState.value = PostDetailState.Error(
                                message = "post $postId not found"
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.value = PostDetailState.Error(
                    message = e.message ?: "error"
                )
            }
        }
    }
    private fun loadComments(post: PostWithAuthor) {
        viewModelScope.launch {
            getCommentsUseCase(postId)
                .catch { error ->
                    if (!isActive) return@catch
                    _uiState.value = PostDetailState.Error(
                        post = post,
                        message = error.message ?: "failed to load comments"
                    )
                }
                .collect { comments ->
                    if (!isActive) return@collect
                    _uiState.value = PostDetailState.Success(
                        post = post,
                        comments = comments
                    )
                }
        }
    }
    fun refresh() {
        loadPostDetail()
    }
}