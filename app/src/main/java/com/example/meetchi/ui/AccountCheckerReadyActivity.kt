package com.example.meetchi.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.meetchi.MainActivity
import com.example.meetchi.model.User
import com.example.meetchi.ui.registration.RegistrationActivity
import com.example.meetchi.ui.theme.MeetchiTheme
import com.example.meetchi.util.AnimationCancel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class AccountCheckerReadyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetchiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val db = Firebase.firestore
                    val userDB = db.collection("User").document(MainActivity.auth.uid.toString())
                    userDB.get().addOnSuccessListener { document ->
                        if (document != null) {
                            val User = document.toObject<User>()
                            if (User != null) {
                                if(User.account_ready == true){
                                    Log.d("Firestore:Log", "DocumentSnapshot data: ${User.account_ready} ${User.nom} ${User.prenom}")
                                    val intent = Intent(this@AccountCheckerReadyActivity, HomeActivity::class.java)
                                    startActivity(intent, AnimationCancel.CancelAnimation(this@AccountCheckerReadyActivity))
                                }
                                else{
                                    val intent = Intent(this@AccountCheckerReadyActivity, RegistrationActivity::class.java)
                                    startActivity(intent, AnimationCancel.CancelAnimation(this@AccountCheckerReadyActivity))
                                }
                            }
                        } else {
                            Log.d("Firestore:Log", "Document Failed")
                            val intent = Intent(this@AccountCheckerReadyActivity, RegistrationActivity::class.java)
                            startActivity(intent, AnimationCancel.CancelAnimation(this@AccountCheckerReadyActivity))
                        }
                    }.addOnFailureListener { exception ->
                        Log.d("Firestore:Log", "get failed with ", exception)
                    }
                }
            }
        }
    }
}