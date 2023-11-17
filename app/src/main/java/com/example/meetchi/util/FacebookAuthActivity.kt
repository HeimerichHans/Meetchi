package com.example.meetchi.util

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.example.meetchi.MainActivity
import com.example.meetchi.ui.AuthActivity
import com.example.meetchi.ui.HomeActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseUser
import java.util.Arrays


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
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    startActivity(Intent(context, AuthActivity::class.java))
                    finish()
                }

                override fun onError(exception: FacebookException) {
                    // Code de l'application en cas d'erreur
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {

        val credential = FacebookAuthProvider.getCredential(token.token)
        MainActivity.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = MainActivity.auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun updateUI(user: FirebaseUser?){
        Log.d("FacebookAuth","Go Home")
        intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}