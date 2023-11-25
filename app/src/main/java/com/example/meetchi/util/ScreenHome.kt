package com.example.meetchi.util

import androidx.annotation.StringRes
import com.example.meetchi.R

sealed class ScreenHome(val route: String, @StringRes val resourceId: Int) {
    object Like : ScreenHome("like", R.string.screen_swipe)
    object Chat : ScreenHome("chat", R.string.screen_chat)
    object Profile : ScreenHome("profile", R.string.screen_profile)
}
