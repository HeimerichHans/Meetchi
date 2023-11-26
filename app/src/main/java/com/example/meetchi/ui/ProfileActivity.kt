package com.example.meetchi.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meetchi.MainActivity
import com.example.meetchi.R
import com.example.meetchi.model.User
import com.example.meetchi.ui.login.AuthActivity
import com.example.meetchi.ui.theme.MeetchiTheme
import com.example.meetchi.util.AnimationCancel
import com.google.firebase.Firebase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import java.util.Calendar

@SuppressLint("UnrememberedMutableState")
@Composable
fun ProfileScreen(userDB: MutableState<User>) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(50.dp))
            ProfilPresentation(userDB)
            Spacer(modifier = Modifier.height(30.dp))
            ProfilParameter(userDB)
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = {
                    if(userDB.value.pseudonyme != null && userDB.value.nom != null && userDB.value.prenom != null && userDB.value.description != null){
                        userDB.value.dateUpdate = Calendar.getInstance().time
                        val db = Firebase.firestore
                        db.collection("User")
                            .document(MainActivity.auth.uid.toString())
                            .set(userDB.value, SetOptions.merge())
                            .addOnSuccessListener { documentReference ->
                                Log.d("Firestore:Log", "Document update")
                            }
                            .addOnFailureListener { e ->
                                Log.d("Firestore:Log", "Error adding document", e)
                            }
                    }
                },
            ) {
                Text(stringResource(R.string.save))
            }
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ){
            Button(
                onClick = {
                    // Handle login action
                    MainActivity.auth.signOut()
                    Log.d("UserStatus", "Logout success")
                    val intent = Intent(context, AuthActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent, AnimationCancel.CancelAnimation(context))
                },
            ) {
                Text(stringResource(R.string.logout))
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun ProfilPresentation(toto : MutableState<User>){
    val calendar = Calendar.getInstance()
    var age = 0
    if(toto.value.dateNaissance != null){
        calendar.time = toto.value.dateNaissance!!
        age = Calendar.getInstance().get(Calendar.YEAR) - calendar.get(Calendar.YEAR)
    }
    Text(
        text = "${toto.value.pseudonyme}, $age",
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
        modifier = Modifier.padding(horizontal = 30.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilParameter(userDB : MutableState<User>){
    userDB.value.pseudonyme?.let {
        TextField(
            value = it,
            onValueChange = { userDB.value = userDB.value.copy(pseudonyme = it) },
            label = { Text(stringResource(R.string.pseudonyme)) },
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
    }
    userDB.value.nom?.let {
        TextField(
            value = it,
            onValueChange = { userDB.value = userDB.value.copy(nom = it) },
            label = { Text(stringResource(R.string.name)) },
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
    }
    userDB.value.prenom?.let {
        TextField(
            value = it,
            onValueChange = { userDB.value = userDB.value.copy(prenom = it) },
            label = { Text(stringResource(R.string.surname)) },
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
    }
    userDB.value.description?.let {
        TextField(
            value = it,
            onValueChange = { userDB.value = userDB.value.copy(description = it) },
            label = { Text(stringResource(R.string.description)) },
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
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    var userDB = User()
    userDB.nom = ""
    userDB.dateNaissance = Calendar.getInstance().time
    userDB.pseudonyme = ""
    userDB.description = ""
    userDB.prenom = ""
    userDB.account_ready = true
    userDB.genre = "1"
    userDB.dateCreation = Calendar.getInstance().time
    userDB.dateUpdate = Calendar.getInstance().time
    MeetchiTheme {
        ProfileScreen(mutableStateOf(userDB))
    }
}