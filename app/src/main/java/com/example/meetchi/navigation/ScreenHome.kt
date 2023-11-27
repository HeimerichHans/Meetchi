package com.example.meetchi.navigation

import androidx.annotation.StringRes
import com.example.meetchi.R

/*
*******************************************************
*                ScreenNav: Home                      *
*******************************************************
|  Description:                                       |
|  Définition des différentes routes possibles        |
|  pour notre NavController de la page HomeActivity   |
*******************************************************
*/
sealed class ScreenHome(val route: String, @StringRes val resourceId: Int) {
    object Like : ScreenHome("like", R.string.screen_swipe)
    object Chat : ScreenHome("chat", R.string.screen_chat)
    object Profile : ScreenHome("profile", R.string.screen_profile)
}
