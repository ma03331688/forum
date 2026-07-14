package com.example.forum.presentation.screens.postdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.forum.R
import com.example.forum.presentation.components.CommentItem
import com.example.forum.presentation.components.ErrorScreen
import com.example.forum.presentation.components.ForumTopBar
import com.example.forum.presentation.components.LoadingIndicator
import com.example.forum.utils.DateFormatter

@Composable
fun PostDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: PostDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onProfileClick: (Int) -> Unit

) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            ForumTopBar(
                title = "Back",
                onBackClick = onBackClick
            )
        },
        modifier = modifier
    ) { innerPadding ->
        when (uiState) {
            is PostDetailState.Loading -> {
                LoadingIndicator(modifier = Modifier.padding(innerPadding))
            }
            is PostDetailState.Success -> {
                val successState = uiState as PostDetailState.Success
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            androidx.compose.foundation.Image(
                                painter = painterResource(id = R.drawable.ic_avatar_default),
                                contentDescription = "Avatar",
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = successState.post.authorName,
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onProfileClick(successState.post.post.userId) }
                        )
                        Text(
                            text = DateFormatter.formatDateShort(successState.post.post.createdAt),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }

                    Text(
                        text = successState.post.post.title.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                        text = successState.post.post.body,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    Text(
                        text = "Comments:",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 12.dp, top = 5.dp)
                    )

                    LazyColumn {
                        items(successState.comments) { comment ->
                            CommentItem(comment = comment)
                        }
                    }
                }
            }
            is PostDetailState.Error -> {
                val errorState = uiState as PostDetailState.Error
                ErrorScreen(
                    message = errorState.message,
                    onRetry = viewModel::refresh
                )
            }
        }
    }
}