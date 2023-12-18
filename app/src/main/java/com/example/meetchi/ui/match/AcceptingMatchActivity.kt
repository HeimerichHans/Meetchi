package com.example.meetchi.ui.match

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.meetchi.ui.theme.MeetchiTheme

class AcceptingMatchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetchiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AcceptingMatch()
                }
            }
        }
    }
}

@Composable
fun AcceptingMatch() {
    Text(
        text = "Hello"
    )
}

@Preview(showBackground = true, device = "id:Samsung S9+", showSystemUi = true)
@Composable
fun AcceptingMatchPreview2() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AcceptingMatch()
    }
}