package com.example.meetchi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.meetchi.R
import com.example.meetchi.ui.theme.MeetchiTheme

/*
********************************************************
*         Activity: SplashScreenActivity               *
********************************************************
|  Description:                                        |
|  Activité pour l'écran de démarrage de l'application.|
|  Affiche un logo animé lors du lancement de          |
|  l'application.                                      |
*******************************************************
*/
class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetchiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Call your composable function here
                    AnimatedLogo()
                }
            }
        }
    }
}

/*
*******************************************************
*       Fonction Composable: AnimatedLogo             *
*******************************************************
|  Description:                                       |
|  Compose l'animation du logo de l'application.      |
|  L'animation consiste en un effet de répétition     |
|  d'échelle sur le logo.                             |
*******************************************************
*/
@Composable
fun AnimatedLogo() {
    // Récupération du painter de l'image du logo
    val imagePainter = painterResource(id = R.drawable.ic_meetchi_foreground)

    // Configuration de l'animation d'échelle infinie
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.35f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "scale"
    )
    // Affichage de l'image avec l'effet d'échelle
    Image(
        painter = imagePainter,
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                transformOrigin = TransformOrigin.Center
            }

    )

}

/*
*******************************************************
*           Preview: AnimatedLogoPreview              *
*******************************************************
|  Description:                                       |
|  Aperçu de l'animation du logo.                     |
*******************************************************
*/
@Preview(showBackground = true, device = "id:Samsung S9+", showSystemUi = true)
@Composable
fun AnimatedLogoPreview(){
    MeetchiTheme{
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AnimatedLogo()
        }
    }
}