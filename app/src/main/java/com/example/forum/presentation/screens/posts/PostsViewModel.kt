package com.example.forum.presentation.screens.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forum.domain.model.PostWithAuthor
import com.example.forum.domain.usecase.GetFirstPostsUseCase
import com.example.forum.domain.usecase.GetNextPostsUseCase
import com.example.forum.domain.usecase.GetPostsWithAuthorsUseCase
import com.example.forum.domain.usecase.RefreshFirstPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsWithAuthorsUseCase: GetPostsWithAuthorsUseCase,
    private val getFirstPostsUseCase: GetFirstPostsUseCase,
    private val getNextPostsUseCase: GetNextPostsUseCase,
    private val refreshFirstPostsUseCase: RefreshFirstPostsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<PostsState>(PostsState.Loading)
    val uiState: StateFlow<PostsState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _allPosts = mutableListOf<PostWithAuthor>()
    private var _isLoadingMore = false
    private var _hasMoreData = true
    private val pageSize = 10
    private var _currentOffset = 0
    private var _allPostsInCache = emptyList<PostWithAuthor>()
    private var _allPostsLoadedFromServer = false

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            try {
                _uiState.value = PostsState.Loading

                val allPosts = getPostsWithAuthorsUseCase().first()

                if (allPosts.isNotEmpty()) {
                    _allPostsInCache = allPosts.sortedByDescending { it.post.id }
                    _allPosts.clear()
                    _allPosts.addAll(_allPostsInCache.take(pageSize))
                    _uiState.value = PostsState.Success(_allPosts.toList())
                    _currentOffset = pageSize
                    _allPostsLoadedFromServer = true

                    refreshFirstPostsInBackground()
                } else {
                    loadFirstPostsFromServer()
                }

            } catch (e: Exception) {
                _uiState.value = PostsState.Error(e.message ?: "unknown error")
                _isRefreshing.value = false
            }
        }
    }

    private fun refreshFirstPostsInBackground() {
        viewModelScope.launch {
            try {
                refreshFirstPostsUseCase(pageSize)

                val updatedAllPosts = getPostsWithAuthorsUseCase().first()
                _allPostsInCache = updatedAllPosts.sortedByDescending { it.post.id }

                _allPosts.clear()
                _allPosts.addAll(_allPostsInCache.take(pageSize))
                _uiState.value = PostsState.Success(_allPosts.toList())
                _currentOffset = pageSize

            } catch (_: Exception) {
            }
        }
    }

    private fun loadFirstPostsFromServer() {
        viewModelScope.launch {
            try {
                val posts = getFirstPostsUseCase(pageSize)

                if (posts.isNotEmpty()) {
                    _allPosts.clear()
                    _allPosts.addAll(posts)
                    _uiState.value = PostsState.Success(_allPosts.toList())
                    _currentOffset = pageSize

                    _allPostsInCache = getPostsWithAuthorsUseCase().first()
                        .sortedByDescending { it.post.id }
                    _allPostsLoadedFromServer = true
                } else {
                    _uiState.value = PostsState.Error("no posts found")
                }
            } catch (e: Exception) {
                _uiState.value = PostsState.Error(e.message ?: "error")
            }
        }
    }

    fun loadMorePosts() {
        if (_isLoadingMore || !_hasMoreData) return

        viewModelScope.launch {
            try {
                _isLoadingMore = true

                val totalInCache = _allPostsInCache.size
                val currentSize = _allPosts.size

                if (currentSize < totalInCache) {
                    val nextPosts = _allPostsInCache.drop(currentSize).take(pageSize)
                    if (nextPosts.isNotEmpty()) {
                        _allPosts.addAll(nextPosts)
                        _uiState.value = PostsState.Success(_allPosts.toList())
                    } else {
                        _hasMoreData = false
                    }
                } else if (!_allPostsLoadedFromServer) {
                    val offset = _currentOffset

                    val newPosts = getNextPostsUseCase(offset, pageSize)

                    if (newPosts.isNotEmpty()) {
                        _allPosts.addAll(newPosts)
                        _uiState.value = PostsState.Success(_allPosts.toList())
                        _currentOffset += newPosts.size

                        _allPostsInCache = getPostsWithAuthorsUseCase().first()
                            .sortedByDescending { it.post.id }
                    } else {
                        _allPostsLoadedFromServer = true
                        _hasMoreData = false
                    }
                } else {
                    _hasMoreData = false
                }

                _isLoadingMore = false
                _isRefreshing.value = false

            } catch (_: Exception) {
                _isLoadingMore = false
                _isRefreshing.value = false
            }
        }
    }

    fun refresh() {
        _isRefreshing.value = true
        viewModelScope.launch {
            try {
                refreshFirstPostsUseCase(pageSize)

                _allPostsInCache = getPostsWithAuthorsUseCase().first()
                    .sortedByDescending { it.post.id }

                _allPosts.clear()
                _allPosts.addAll(_allPostsInCache.take(pageSize))
                _uiState.value = PostsState.Success(_allPosts.toList())
                _currentOffset = pageSize
                _hasMoreData = true
                _allPostsLoadedFromServer = true

                _isRefreshing.value = false

            } catch (e: Exception) {
                _uiState.value = PostsState.Error(e.message ?: "error")
                _isRefreshing.value = false
            }
        }
    }
}