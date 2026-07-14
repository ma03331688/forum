package com.example.forum.presentation.navigation

sealed class NavigationDestination (val route: String) {
    data object Posts : NavigationDestination("posts")
    data class PostDetail(val postId: Int) : NavigationDestination("post_detail/{postId}"){
        fun passArgs(): String="post_detail/$postId"
    }
    data class UserProfile(val userId: Int) : NavigationDestination("user_profile/{userId}"){
        fun passArgs(): String = "user_profile/$userId"
    }
}