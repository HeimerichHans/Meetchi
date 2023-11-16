package com.example.meetchi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.meetchi.ui.AnimatedLogo
import com.example.meetchi.ui.AuthActivity
import com.example.meetchi.ui.HomeActivity
import com.example.meetchi.ui.theme.MeetchiTheme
import com.google.firebase.auth.FirebaseAuth

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
                AnimatedLogo()
                if( user!= null){
                    Log.d("UserStatus","Connected")
                    intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Log.d("UserStatus","Not Connected")
                    intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}