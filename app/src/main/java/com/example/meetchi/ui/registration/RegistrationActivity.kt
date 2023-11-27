package com.example.meetchi.ui.registration

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.meetchi.model.User
import com.example.meetchi.ui.theme.MeetchiTheme
import com.example.meetchi.navigation.ScreenRegister

/*
*******************************************************
*         Activity: RegistrationActivity              *
*******************************************************
|  Description:                                       |
|  Activité principale pour le processus d'inscription|
|  d'un nouvel utilisateur. Initialise l'utilisateur  |
|  et définit le thème global de l'application.       |
*******************************************************
*/
class RegistrationActivity : ComponentActivity() {

    companion object{
        lateinit var user: User
        lateinit var context: Context
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = User()
        setContent {
            MeetchiTheme {
                context = LocalContext.current
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegisterNavigator()
                }
            }
        }
    }
}

/*
******************************************************
*      Fonction: RegisterNavigator                   *
******************************************************
|  Description:                                      |
|  Compose le composant de navigation pour guider    |
|  l'utilisateur à travers les étapes du processus   |
|  d'inscription. Utilise le NavController pour      |
|  naviguer entre les écrans.                        |
******************************************************
*/
@Composable
fun RegisterNavigator(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenRegister.Identity.route ){
        composable(ScreenRegister.Identity.route) {
            ScreenIdentity(navController = navController)
        }
        composable(ScreenRegister.Gender.route) {
            ScreenGender(navController = navController)
        }
        composable(ScreenRegister.Description.route) {
            ScreenDescription(navController = navController)
        }
        composable(ScreenRegister.PhoneNumber.route) {
            ScreenPhoneNumber(navController = navController)
        }
    }
}