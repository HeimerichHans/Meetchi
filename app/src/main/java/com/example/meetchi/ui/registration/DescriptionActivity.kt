package com.example.meetchi.ui.registration

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import android.util.Log
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.meetchi.MainActivity
import com.example.meetchi.R
import com.example.meetchi.util.AccountCheckerReadyActivity
import com.example.meetchi.ui.theme.MeetchiTheme
import com.example.meetchi.util.AnimationCancel
import com.google.firebase.Firebase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Calendar

/*
*******************************************************
*            Fragment: ScreenDescription              *
*******************************************************
|  Description:                                       |
|  Compose l'écran de description du profil           |
|  lors de l'inscription.                             |
*******************************************************
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenDescription(navController: NavController, modifier: Modifier = Modifier) {
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background)
    {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            // États pour stocker le pseudonyme et la description
            var description by remember { mutableStateOf("") }
            var pseudonyme by remember { mutableStateOf("") }
            val context = LocalContext.current
            // Champ de texte pour le pseudonyme
            TextField(
                value = pseudonyme,
                onValueChange = { pseudonyme = it },
                label = { Text(stringResource(R.string.pseudonyme)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            )
            // Champ de texte pour la description
            TextField(
                value = description,
                onValueChange = {
                    // Limitez la longueur de la description à 255 caractères
                    if( it.length < 255 ){
                        description = it
                    }
                                },
                label = { Text(stringResource(R.string.description)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            )
            // Bouton pour soumettre le formulaire
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ){
                Button(
                    onClick = {
                        // Enregistrez les informations dans Firestore
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                        if(description != null && pseudonyme != null){
                            val db = Firebase.firestore
                            RegistrationActivity.user.pseudonyme = pseudonyme
                            RegistrationActivity.user.description = description
                            RegistrationActivity.user.dateCreation = Calendar.getInstance().time
                            RegistrationActivity.user.dateUpdate = Calendar.getInstance().time
                            RegistrationActivity.user.account_ready = true
                            db.collection("User")
                                .document(MainActivity.auth.uid.toString())
                                .set(RegistrationActivity.user, SetOptions.merge())
                                .addOnSuccessListener { documentReference ->
                                    Log.d("Firestore:Log", "DocumentSnapshot added")
                                    val intent = Intent(context, AccountCheckerReadyActivity::class.java)
                                    context?.startActivity(intent, AnimationCancel.CancelAnimation(context))
                                }
                                .addOnFailureListener { e ->
                                    Log.d("Firestore:Log", "Error adding document", e)
                                }
                        }
                    },
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    contentPadding = PaddingValues(3.dp),
                ){
                    // Icône flèche vers l'avant
                    Icon(imageVector = Icons.Rounded.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

/*
*******************************************************
*           Preview: PhoneNumberPreview               *
*******************************************************
|  Description:                                       |
|  Fonction de prévisualisation pour l'écran de       |
|  description du profil lors de l'inscription.       |
*******************************************************
*/
@Preview(showBackground = true)
@Composable
private fun PhoneNumberPreview() {
    val navController = rememberNavController()
    MeetchiTheme{
        ScreenDescription(navController = navController)
    }
}