package com.example.forum.presentation.screens.userprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.forum.R
import com.example.forum.presentation.components.ErrorScreen
import com.example.forum.presentation.components.ForumTopBar
import com.example.forum.presentation.components.LoadingIndicator

@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: UserProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit
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
            is UserProfileState.Loading -> {
                LoadingIndicator(modifier = Modifier.padding(innerPadding))
            }
            is UserProfileState.Success -> {
                val successState = uiState as UserProfileState.Success
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_avatar_default),
                                contentDescription = "Avatar",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = successState.user.name,
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        Text(
                            text = successState.user.email,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(top = 4.dp),
                        )
                    }
                    Text(
                        text = "About Me:",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(
                            bottom = 12.dp,
                            top = 25.dp
                        )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "Phone:",
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.alignByBaseline()
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = successState.user.phone,
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.alignByBaseline()
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "Website:",
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.alignByBaseline()
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = successState.user.website,
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.alignByBaseline()
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "Company:",
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.alignByBaseline()
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = successState.user.companyName,
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.alignByBaseline()
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                }
            }
            is UserProfileState.Error -> {
                val errorState = uiState as UserProfileState.Error
                ErrorScreen(
                    message = errorState.message,
                    onRetry = viewModel::refresh
                )
            }
        }
    }
}