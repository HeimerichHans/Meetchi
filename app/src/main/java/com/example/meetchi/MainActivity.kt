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

class MainActivity : ComponentActivity() {

    companion object{
        lateinit var auth: FirebaseAuth
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        setContent {
            MeetchiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AnimatedLogo()

                    LaunchedEffect(key1 = true) {
                        delay(2000)

                        // Execute the code after the delay
                        CoroutineScope(Dispatchers.Main).launch {

                            if (user != null) {
                                Log.d("UserStatus", "Connected")
                                val intent = Intent(this@MainActivity, AccountCheckerReadyActivity::class.java)
                                startActivity(intent, AnimationCancel.CancelAnimation(this@MainActivity))
                            } else {
                                Log.d("UserStatus", "Not Connected")
                                val intent = Intent(this@MainActivity, AuthActivity::class.java)
                                startActivity(intent, AnimationCancel.CancelAnimation(this@MainActivity))
                            }

                            // Finish the current activity
                            finish()
                        }
                    }
                }

            }
        }
    }
}