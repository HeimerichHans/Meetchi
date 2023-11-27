package com.example.meetchi.util

import android.app.ActivityOptions
import android.content.Context
import android.os.Bundle

object AnimationCancel {
    /*
    *************************************************************
    *                Fonction: CancelAnimation                  *
    *************************************************************
    |  Description:                                             |
    |  Cette fonction retourne un bundle permettant d'annuler   |
    |  l'animation d'une transition entre les activités.        |
    *************************************************************
    */
    fun CancelAnimation(context: Context): Bundle {

        val customAnimation = ActivityOptions.makeCustomAnimation(
            context,
            0,
            0
        )
        return customAnimation.toBundle()
    }
}