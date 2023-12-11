package com.example.meetchi.util

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.example.meetchi.MainActivity
import com.example.meetchi.ui.login.AuthActivity
import com.example.meetchi.ui.HomeActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.Arrays

/*
**********************************************************
*            Activity: FacebookAuthActivity              *
**********************************************************
|  Description:                                          |
|  Gère l'authentification via Facebook. Utilise le      |
|  SDK Facebook pour obtenir l'accès, puis utilise les   |
|  informations d'accès pour authentifier l'utilisateur  |
|  auprès de Firebase. En fonction du résultat,          |
|  l'utilisateur est redirigé vers l'écran d'accueil ou  |
|  l'écran d'authentification de l'application.          |
**********************************************************
*/
class FacebookAuthActivity : ComponentActivity() {
    var callbackManager = CallbackManager.Factory.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            Log.d("FacebookAuth","Start")
            LoginManager.getInstance().logInWithReadPermissions(this,Arrays.asList("public_profile"))
            LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d("UserStatus","Connection Success")
                    // Gère le jeton d'accès Facebook pour authentifier l'utilisateur sur Firebase
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    // En cas d'annulation, redirige vers l'écran d'authentification
                    startActivity(Intent(context, AuthActivity::class.java), AnimationCancel.CancelAnimation(this@FacebookAuthActivity))
                    finish()
                }

                override fun onError(exception: FacebookException) {
                    // Code de l'application en cas d'erreur
                }
            })
        }
    }

    /*
    ******************************************************
    *           Fonction: onActivityResult               *
    ******************************************************
    |  Description:                                      |
    |  Appelée lorsque le résultat d'une activité est    |
    |  disponible. Transmet le résultat au SDK Facebook. |
    ******************************************************
    */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    /*
    ********************************************************
    *      Fonction: handleFacebookAccessToken             *
    ********************************************************
    |  Description:                                        |
    |  Utilise le jeton d'accès Facebook pour créer des    |
    |  informations d'identification Firebase. En fonction |
    |  du résultat, met à jour l'interface utilisateur ou  |
    |  affiche un message d'échec d'authentification.      |
    ********************************************************
    */
    private fun handleFacebookAccessToken(token: AccessToken) {
        // Crée des informations d'identification Firebase avec le jeton d'accès Facebook
        val credential = FacebookAuthProvider.getCredential(token.token)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Connexion réussie, met à jour l'interface utilisateur avec les informations de l'utilisateur connecté
                    val user = FirebaseAuth.getInstance().currentUser
                    updateUI(user)
                } else {
                    // En cas d'échec de la connexion, affiche un message à l'utilisateur
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    /*
    ******************************************************
    *              Fonction: updateUI                    *
    ******************************************************
    |  Description:                                      |
    |  Met à jour l'interface utilisateur en fonction    |
    |  des informations de l'utilisateur connecté après  |
    |  une connexion réussie. Redirige vers l'écran      |
    |  d'accueil de l'application.                       |
    ******************************************************
    */
    private fun updateUI(user: FirebaseUser?){
        Log.d("FacebookAuth","Go Home")
        intent = Intent(this, HomeActivity::class.java)
        startActivity(intent, AnimationCancel.CancelAnimation(this@FacebookAuthActivity))
        finish()
    }
}