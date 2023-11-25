package com.example.meetchi.util

import androidx.annotation.StringRes
import com.example.meetchi.R

sealed class ScreenRegister(val route: String, @StringRes val resourceId: Int){
    object Identity : ScreenRegister("identity", R.string.screen_identity)
    object Gender : ScreenRegister("gender", R.string.screen_gender)
    object PhoneNumber : ScreenRegister("phone", R.string.screen_phone)
    object Description : ScreenRegister("description", R.string.screen_description)
}
