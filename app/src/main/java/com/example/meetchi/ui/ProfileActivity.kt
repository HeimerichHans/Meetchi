package com.example.meetchi.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.meetchi.MainActivity
import com.example.meetchi.R
import com.example.meetchi.ui.theme.MeetchiTheme

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetchiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProfileScreen()
                }
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    val intent = LocalContext.current
    Button(
        onClick = {
            // Handle login action
            MainActivity.auth.signOut()
            Log.d("UserStatus", "Logout success")
        },
    ) {
        Text(stringResource(R.string.logout))
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    MeetchiTheme {
        ProfileScreen()
    }
}