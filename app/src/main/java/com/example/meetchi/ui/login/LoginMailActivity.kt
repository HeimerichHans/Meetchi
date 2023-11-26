package com.example.meetchi.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.example.meetchi.util.AccountCheckerReadyActivity
import com.example.meetchi.ui.theme.MeetchiTheme
import com.example.meetchi.util.AnimationCancel
import com.example.meetchi.util.BackArrowAuth
import com.example.meetchi.util.IconAuth

class MailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetchiTheme {
                PageMail()
            }
        }
    }

    @Composable
    private fun PageMail()
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
                Spacer(modifier = Modifier.height(110.dp))
                IconAuth()
                Spacer(modifier = Modifier.height(40.dp))
                Mail()
                InscriptionMail()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Mail(modifier: Modifier = Modifier) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val isLoginEnabled by remember {
            derivedStateOf {
                username.isNotBlank() && password.isNotBlank()
            }
        }

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
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
                        // Move focus to the next text field or perform any action
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 5.dp)
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.password)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Handle login action
                        if (isLoginEnabled) {
                            performLogin(username, password)
                        }
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 5.dp)
            )
            Spacer(modifier.height(60.dp))
            Button(
                onClick = {
                    // Handle login action
                    if (isLoginEnabled) {
                        performLogin(username, password)
                    }
                },
                enabled = isLoginEnabled
            ) {
                Text(stringResource(R.string.login))
            }
        }
    }

    @Composable
    fun InscriptionMail()
    {
        val context = LocalContext.current
        Column (modifier = Modifier
            .padding(30.dp),
            horizontalAlignment = Alignment.Start)
        {
            Text(text = stringResource(R.string.AskMember),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .height(30.dp)
                    .fillMaxSize())
            TextButton(onClick = {
                intent = Intent(context, RegisterMailActivity::class.java)
                startActivity(intent, AnimationCancel.CancelAnimation(this@MailActivity))
                finish()
            })
            {
                Text(stringResource(R.string.register))
            }
        }
    }

    fun performLogin(username: String, password: String) {
        MainActivity.auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("UserStatus", "Connection success")
                    startActivity(Intent(this, AccountCheckerReadyActivity::class.java), AnimationCancel.CancelAnimation(this@MailActivity))
                    finish()
                } else {
                    Log.d("UserStatus", "Connection failed")
                }
            }
    }

    @Preview(showBackground = true)
    @Composable
    private fun PageMailPreview() {
        MeetchiTheme{
            PageMail()
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun MailPreview() {
        MeetchiTheme(){
            Mail()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun InscriptionMailPreview() {
        MeetchiTheme {
            InscriptionMail()
        }
    }
}

