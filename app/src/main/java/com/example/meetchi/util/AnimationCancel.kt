package com.example.meetchi.util

import android.app.ActivityOptions
import android.content.Context
import android.os.Bundle

object AnimationCancel {
    fun CancelAnimation(context: Context): Bundle {

        val customAnimation = ActivityOptions.makeCustomAnimation(
            context,
            0,
            0
        )
        return customAnimation.toBundle()
    }
}