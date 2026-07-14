package com.example.forum.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.forum.presentation.screens.postdetail.PostDetailScreen
import com.example.forum.presentation.screens.posts.PostsScreen
import com.example.forum.presentation.screens.userprofile.UserProfileScreen

@Composable
fun NavGraph (
    modifier: Modifier= Modifier
){
    val navController=rememberNavController()
    NavHost(
        navController=navController,
        startDestination = NavigationDestination.Posts.route,
        modifier=modifier
    ){
        composable (NavigationDestination.Posts.route){
            PostsScreen(
                onPostClick = { postId ->
                    navController.navigate(
                        NavigationDestination.PostDetail(postId).passArgs()
                    )
                },
                onAuthorClick = { userId ->
                    navController.navigate(
                        NavigationDestination.UserProfile(userId).passArgs()
                    )
                }
            )
        }
        composable (
            route= NavigationDestination.PostDetail(0).route,
            arguments = listOf(
                navArgument("postId") {type=NavType.IntType}
            )
        ){ backStackEntry ->
            val postId = backStackEntry.arguments?.getInt("postId") ?:0
            PostDetailScreen(
                onBackClick = {navController.popBackStack()},
                onProfileClick = { userId ->
                    navController.navigate(
                        NavigationDestination.UserProfile(userId).passArgs()
                    )
                }
            )
        }
        composable(
            route= NavigationDestination.UserProfile(0).route,
            arguments = listOf(
                navArgument("userId"){type=NavType.IntType}
            )
        ){ backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            UserProfileScreen(
                onBackClick = {navController.popBackStack()}
            )
        }
    }
}