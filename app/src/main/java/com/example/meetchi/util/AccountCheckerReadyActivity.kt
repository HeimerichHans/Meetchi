package com.example.meetchi.util

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
import com.example.meetchi.ui.HomeActivity
import com.example.meetchi.ui.registration.RegistrationActivity
import com.example.meetchi.ui.theme.MeetchiTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

/*
*********************************************************************************
*              Activity: AccountCheckerReadyActivity                            *
*********************************************************************************
|  Description:                                                                 |
|  Cette activité vérifie si le compte de l'utilisateur est prêt. Elle          |
|  récupère les informations utilisateur à partir de Firebase Firestore et      |
|  redirige l'utilisateur vers l'activité d'accueil si le compte est prêt,      |
|  sinon elle le redirige vers l'activité d'enregistrement.                     |
*********************************************************************************
*/
class AccountCheckerReadyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetchiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Obtenir une référence à la base de données Firestore
                    val db = Firebase.firestore
                    // Obtenir une référence au document utilisateur dans Firestore
                    val userDB = db.collection("User").document(FirebaseAuth.getInstance().uid.toString())
                    // Récupérer les données du document utilisateur
                    userDB.get().addOnSuccessListener { document ->
                        if (document != null) {
                            // Convertir le document Firestore en objet User
                            val User = document.toObject<User>()
                            if (User != null) {
                                if(User.account_ready == true){
                                    var token: String? = null
                                    runBlocking {
                                        User.token = Firebase.messaging.token.await()
                                    }
                                    val database = Firebase.firestore
                                    database.collection("User")
                                        .document(FirebaseAuth.getInstance().uid.toString())
                                        .set(User)
                                        .addOnSuccessListener {
                                            Log.d("Firestore:Log", "User: ${User.uid} Token: ${User.token}")
                                            // Rediriger vers l'activité d'accueil si le compte est prêt
                                            Log.d("Firestore:Log", "DocumentSnapshot data: ${User.account_ready} ${User.nom} ${User.prenom}")
                                            val intent = Intent(this@AccountCheckerReadyActivity, HomeActivity::class.java)
                                            startActivity(intent, AnimationCancel.CancelAnimation(this@AccountCheckerReadyActivity))
                                        }
                                }
                                else{
                                    // Rediriger vers l'activité d'enregistrement si le compte n'est pas prêt
                                    val intent = Intent(this@AccountCheckerReadyActivity, RegistrationActivity::class.java)
                                    startActivity(intent, AnimationCancel.CancelAnimation(this@AccountCheckerReadyActivity))
                                }
                            }
                            else{
                                // Rediriger vers l'activité d'enregistrement si le compte n'est pas prêt
                                Log.d("Firestore:Log", "User Failed")
                                val intent = Intent(this@AccountCheckerReadyActivity, RegistrationActivity::class.java)
                                startActivity(intent, AnimationCancel.CancelAnimation(this@AccountCheckerReadyActivity))
                            }
                        } else {
                            // Rediriger vers l'activité d'enregistrement si le compte n'est pas prêt
                            Log.d("Firestore:Log", "Document Failed")
                            val intent = Intent(this@AccountCheckerReadyActivity, RegistrationActivity::class.java)
                            startActivity(intent, AnimationCancel.CancelAnimation(this@AccountCheckerReadyActivity))
                        }
                    }.addOnFailureListener { exception ->
                        // Gérer les échecs lors de la récupération des données Firestore
                        Log.d("Firestore:Log", "get failed with ", exception)
                    }
                }
            }
        }
    }
}