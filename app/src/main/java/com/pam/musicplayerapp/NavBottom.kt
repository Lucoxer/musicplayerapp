package com.pam.musicplayerapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavBottom(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Play:NavBottom(
        route = "play",
        title = "Play",
        icon = Icons.Default.PlayArrow
    )object Favourite:NavBottom(
        route = "favorite",
        title = "Favourite",
        icon = Icons.Default.FavoriteBorder
    )object Search:NavBottom(
        route = "search",
        title = "Search",
        icon = Icons.Default.Search
    )object Profile:NavBottom(
        route = "account",
        title = "Account",
        icon = Icons.Default.AccountCircle
    )
}
