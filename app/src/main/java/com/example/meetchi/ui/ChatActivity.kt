package com.example.meetchi.ui


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.meetchi.ui.theme.MeetchiTheme

/*
*******************************************************
*               Fragment: ChatActivity                *
*******************************************************
|  Description:                                       |
|  En cours de conception                             |
*******************************************************
*/
@Composable
fun ChatScreen() {
    Text(
        text = "Chat"
    )
}

@Preview(showBackground = true, device = "id:Samsung S9+", showSystemUi = true)
@Composable
fun GreetingPreview() {
    MeetchiTheme {
        ChatScreen()
    }
}