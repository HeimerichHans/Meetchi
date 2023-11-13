package com.example.meetchi

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.meetchi.ui.theme.MeetchiTheme
import kotlinx.coroutines.delay

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

@Composable
fun AnimatedLogo() {
    val imagePainter = painterResource(id = R.drawable.ic_launcher_foreground)

    val numberOfLoops = 30
    val animationDuration = 1000 // Set the desired animation duration in milliseconds

    var isZoomed by remember { mutableStateOf(false) }
    var loopCounter by remember { mutableStateOf(0) }

    val scale by animateFloatAsState(
        targetValue = if (isZoomed) 1.3f else 1f,
        animationSpec = tween(durationMillis = animationDuration.toInt()), label = ""
    )

    LaunchedEffect(scale) {
        repeat(numberOfLoops) {
            isZoomed = true
            delay(animationDuration.toLong())
            isZoomed = false
            delay(animationDuration.toLong())
        }
        loopCounter++
    }

    Image(
        painter = imagePainter,
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .scale(scale)
    )
}


@Preview
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