package com.example.meetchi.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meetchi.MainActivity
import com.example.meetchi.R
import com.example.meetchi.ui.TermsServicesActivity
import com.example.meetchi.ui.theme.MeetchiTheme
import com.example.meetchi.util.AccountCheckerReadyActivity
import com.example.meetchi.util.AnimationCancel
import com.example.meetchi.util.BackArrowAuth
import com.example.meetchi.util.IconAuth

/*
*******************************************************
*         Activity: RegisterMailActivity              *
*******************************************************
|  Description:                                       |
|  Activité gérant l'inscription par email.           |
*******************************************************
*/
class RegisterMailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetchiTheme {
                PageRegisterMail()
            }
        }
    }

    /*
    *******************************************************
    *        Fonction Composable: PageRegisterMail        *
    *******************************************************
    |  Description:                                       |
    |  Compose la page d'inscription par email.           |
    *******************************************************
    */
    @Composable
    private fun PageRegisterMail()
    {
        Surface (
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){
            BackArrowAuth(LocalContext.current)
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement  = Arrangement.Top
            ){
                Spacer(modifier = Modifier.height(60.dp))
                IconAuth()
                Spacer(modifier = Modifier.height(40.dp))
                RegisterMail()
            }
        }
    }

    /*
    *******************************************************
    *         Fonction Composable: RegisterMail           *
    *******************************************************
    |  Description:                                       |
    |  Compose la section d'inscription par email.        |
    *******************************************************
    */
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
    @Composable
    private fun RegisterMail(modifier: Modifier = Modifier) {
        val focusRequesterPswd = remember { FocusRequester() }
        val focusRequesterRPswd = remember { FocusRequester() }
        val inputService = LocalSoftwareKeyboardController.current
        // Les états pour stocker le nom d'utilisateur, le mot de passe, la confirmation de mot de passe,
        // l'acceptation des conditions d'utilisation, et l'état d'activation du bouton d'inscription.
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var isTermsAccepted by remember { mutableStateOf(false) }
        val isLoginEnabled by remember {
            derivedStateOf {
                username.isNotBlank() &&
                        password.isNotBlank() &&
                        confirmPassword.isNotBlank() &&
                        password == confirmPassword &&
                        isTermsAccepted
            }
        }

        val context = LocalContext.current

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            Row(
                horizontalArrangement = Arrangement.Start)
            {
                Text(text = stringResource(R.string.regregister),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxSize()
                        .padding(start = 20.dp, bottom = 20.dp))
            }
            // Champ de texte pour le nom d'utilisateur
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(R.string.mail)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusRequesterPswd.requestFocus()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 5.dp)
            )
            // Champ de texte pour le mot de passe
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.password)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusRequesterRPswd.requestFocus()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 5.dp)
                    .focusRequester(focusRequesterPswd)
            )
            // Champ de texte pour la confirmation de mot de passe
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text(stringResource(R.string.confirmPassword)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Handle login action
                        if (isLoginEnabled) {
                            performRegister(username, password)
                        }
                        inputService?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 5.dp)
                    .focusRequester(focusRequesterRPswd)
            )
            // Ligne pour l'acceptation des conditions d'utilisation
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Checkbox(
                    checked = isTermsAccepted,
                    onCheckedChange = { isTermsAccepted = it },
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(stringResource(R.string.termsAccept))
                TextButton(onClick = {
                    //INTENT TERMS
                    val intent = Intent(context, TermsServicesActivity::class.java)
                    context.startActivity(intent)
                })
                {
                    Text(stringResource(R.string.terms))
                }
            }
            Spacer(modifier.height(10.dp))
            // Bouton d'inscription
            Button(
                onClick = {
                    // Handle login action
                    if (isLoginEnabled) {
                        performRegister(username, password)
                    }
                },
                enabled = isLoginEnabled
            ) {
                Text(stringResource(R.string.register))
            }
            // Lien vers la page de connexion
            AlreadyRegister()
        }
    }

   /*
   *******************************************************
   *          Fonction Composable: performRegister       *
   *******************************************************
   |  Description:                                       |
   |  Effectue l'inscription de l'utilisateur avec les   |
   |  informations fournies.                             |
   *******************************************************
   */
    fun performRegister(username: String, password: String) {
        MainActivity.auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Succès de l'inscription, mettre à jour l'interface utilisateur avec les informations de l'utilisateur connecté
                    Log.d("UserStatus", "register success")
                    startActivity(Intent(this, AccountCheckerReadyActivity::class.java), AnimationCancel.CancelAnimation(this@RegisterMailActivity))
                    finish()
                } else {
                    // Échec de l'inscription, gérer l'erreur
                    Log.d("UserStatus", "register failed: "+task.exception?.message)
                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
                }
            }
    }

    /*
    *******************************************************
    *      Fonction Composable: AlreadyRegister           *
    *******************************************************
    |  Description:                                       |
    |  Compose le lien vers la page de connexion pour les |
    |  utilisateurs déjà inscrits.                        |
    *******************************************************
    */
    @Composable
    fun AlreadyRegister()
    {
        val context = LocalContext.current
        Column (modifier = Modifier
            .padding(20.dp),
            horizontalAlignment = Alignment.Start)
        {
            Text(text = stringResource(R.string.AskAlreadyMember),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .height(30.dp)
                    .fillMaxSize())
            // Bouton de lien vers la page de connexion
            TextButton(onClick = {
                intent = Intent(context, MailActivity::class.java)
                startActivity(intent, AnimationCancel.CancelAnimation(this@RegisterMailActivity))
                finish()
            })
            {
                Text(stringResource(R.string.login))
            }
        }
    }

    /*
    *******************************************************
    *      Preview: PageRegisterMailPreview               *
    *******************************************************
    |  Description:                                       |
    |  Fonction de prévisualisation pour la page          |
    |  d'inscription par email.                           |
    *******************************************************
    */
    @Preview(showBackground = true, device = "id:Samsung S9+", showSystemUi = true)
    @Composable
    private fun PageMailPreview() {
        MeetchiTheme{
            PageRegisterMail()
        }
    }

    /*
    *******************************************************
    *       Preview: RegisterMailPreview                  *
    *******************************************************
    |  Description:                                       |
    |  Fonction de prévisualisation pour la section       |
    |  d'inscription par email.                           |
    *******************************************************
    */
    @Preview(showBackground = true)
    @Composable
    private fun MailPreview() {
        MeetchiTheme(){
            RegisterMail()
        }
    }
}