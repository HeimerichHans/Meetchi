package com.example.meetchi.ui.registration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.meetchi.ui.theme.MeetchiTheme

/*
*******************************************************
*           Fragment: ScreenPhoneNumber               *
*******************************************************
|  Description:                                       |
|  En cours de conception                             |
*******************************************************
*/
@Composable
fun ScreenPhoneNumber(navController: NavController, modifier: Modifier = Modifier) {

}

@Preview(showBackground = true, device = "id:Samsung S9+", showSystemUi = true)
@Composable
private fun PhoneNumberPreview() {
    val navController = rememberNavController()
    MeetchiTheme{
        ScreenPhoneNumber(navController = navController)
    }
}