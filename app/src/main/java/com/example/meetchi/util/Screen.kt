package com.example.meetchi.util

import androidx.annotation.StringRes
import com.example.meetchi.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Like : Screen("like", R.string.screen_swipe)
    object Chat : Screen("chat", R.string.screen_chat)
    object Profile : Screen("profile", R.string.screen_profile)
}
