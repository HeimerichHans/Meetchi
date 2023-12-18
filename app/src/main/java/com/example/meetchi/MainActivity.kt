package com.example.meetchi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.meetchi.util.AccountCheckerReadyActivity
import com.example.meetchi.ui.AnimatedLogo
import com.example.meetchi.ui.login.AuthActivity
import com.example.meetchi.ui.theme.MeetchiTheme
import com.example.meetchi.util.AnimationCancel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
*******************************************************
*                Activity: MainActivity               *
*******************************************************
|  Description:                                       |
|  Activité principale de l'application. Gère le      |
|  processus de lancement, vérifie si l'utilisateur   |
|  est connecté et navigue vers l'écran approprié en  |
|  conséquence. Utilise également une animation du    |
|  logo au démarrage.                                 |
*******************************************************
*/
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        setContent {
            MeetchiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Affiche une animation de logo au démarrage
                    AnimatedLogo()
                    LaunchedEffect(key1 = true) {
                        delay(2000)

                        // Exécute le code après le délai
                        CoroutineScope(Dispatchers.Main).launch {

                            if (user != null) {
                                // Navigue vers l'écran d'activité home si déjà connecté
                                Log.d("UserStatus", "Connected")
                                val intent = Intent(this@MainActivity, AccountCheckerReadyActivity::class.java)
                                startActivity(intent, AnimationCancel.CancelAnimation(this@MainActivity))
                            } else {
                                // Navigue vers l'écran d'authentification
                                Log.d("UserStatus", "Not Connected")
                                val intent = Intent(this@MainActivity, AuthActivity::class.java)
                                startActivity(intent, AnimationCancel.CancelAnimation(this@MainActivity))
                            }

                            // Termine l'activité actuelle
                            finish()
                        }
                    }
                }

            }
        }
    }
}