package com.example.forum.presentation.screens.posts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.forum.presentation.components.ErrorScreen
import com.example.forum.presentation.components.LoadingIndicator
import com.example.forum.presentation.components.PostCard
import com.example.forum.presentation.components.PostFilterRow

@Composable
fun PostsScreen(
    modifier: Modifier = Modifier,
    viewModel: PostsViewModel = hiltViewModel(),
    onPostClick: (Int) -> Unit,
    onAuthorClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex != null) {
                    val totalItems = when (uiState) {
                        is PostsState.Success -> (uiState as PostsState.Success).posts.size
                        else -> 0
                    }
                    if (lastVisibleIndex >= totalItems - 3 && totalItems > 0) {
                        viewModel.loadMorePosts()
                    }
                }
            }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = "++Forum",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = "Where cpp devs meet and grow",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 14.dp)
                )
                PostFilterRow()
            }

            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.refresh() },
                modifier = Modifier.fillMaxSize()
            ) {
                when (uiState) {
                    is PostsState.Loading -> {
                        if (!isRefreshing) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                LoadingIndicator()
                            }
                        }
                    }
                    is PostsState.Success -> {
                        val successState = uiState as PostsState.Success
                        LazyColumn(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            state = listState
                        ) {
                            items(successState.posts) { post ->
                                PostCard(
                                    post = post,
                                    onClick = { onPostClick(post.post.id) },
                                    onAuthorClick = { onAuthorClick(post.post.userId) }
                                )
                            }
                        }
                    }
                    is PostsState.Error -> {
                        val errorState = uiState as PostsState.Error
                        ErrorScreen(
                            message = errorState.message,
                            onRetry = viewModel::refresh
                        )
                    }
                }
            }
        }
    }
}