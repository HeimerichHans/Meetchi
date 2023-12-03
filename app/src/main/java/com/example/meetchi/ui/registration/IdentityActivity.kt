package com.example.meetchi.ui.registration

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.meetchi.R
import com.example.meetchi.ui.theme.MeetchiTheme
import com.example.meetchi.navigation.ScreenRegister
import java.text.SimpleDateFormat

/*
*******************************************************
*              Fragment: ScreenIdentity               *
*******************************************************
|  Description:                                       |
|  Compose l'écran de saisie des informations         |
|  d'identité de l'utilisateur lors de l'inscription. |
*******************************************************
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenIdentity(navController: NavController, modifier: Modifier = Modifier) {
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background)
    {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            // Variables pour stocker les saisies de l'utilisateur
            var surname by remember { mutableStateOf("") }
            var name by remember { mutableStateOf("") }
            var dateBirth by remember { mutableStateOf("") }
            // Champ de texte pour le prénom
            TextField(
                value = surname,
                onValueChange = { surname = it },
                label = { Text(stringResource(R.string.surname)) },
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
            // Champ de texte pour le nom de famille
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.name)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            )
            // Champ de texte pour la date de naissance
            TextField(
                value = dateBirth,
                onValueChange = { newInput ->
                    val (formattedValue, newPosition) = formatDateString(newInput, dateBirth)
                    dateBirth = formattedValue
                    // Mettez à jour la position du curseur A CORRIGER
                },
                label = { Text(stringResource(R.string.dateBirth)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            )
            // Bouton pour passer à l'écran suivant (le genre)
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ){
                Button(
                    onClick = {
                        // Vérifie la validité des entrées et passe à l'écran suivant
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                        if(name != null && surname != null && isValidDateFormat(dateBirth)){
                            RegistrationActivity.user.nom = name
                            RegistrationActivity.user.prenom = surname
                            RegistrationActivity.user.dateNaissance = dateFormat.parse(dateBirth)
                            navController.navigate(ScreenRegister.Gender.route)
                        }
                    },
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    contentPadding = PaddingValues(3.dp),
                ){
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
*      Fonction: isValidDateFormat                    *
*******************************************************
|  Description:                                       |
|  Vérifie si la chaîne de caractères représentant    |
|  une date est au format valide (dd/MM/yyyy).        |
*******************************************************
*/
private fun isValidDateFormat(dateString: String): Boolean {
    return try {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        dateFormat.isLenient = false
        dateFormat.parse(dateString)
        true
    } catch (e: Exception) {
        false
    }
}

/*
*******************************************************
*      Fonction: formatDateString                     *
*******************************************************
|  Description:                                       |
|  Formate la chaîne de caractères représentant une   |
|  date au fur et à mesure de la saisie, en ajoutant  |
|  automatiquement des barres obliques pour suivre le |
|  format (dd/MM/yyyy).                               |
*******************************************************
*/
private fun formatDateString(input: String, currentValue: String): Pair<String, Int> {
    var formattedString = StringBuilder()
    var digitCount = 0
    var newPosition = 0

    for (i in input.indices) {
        if (input[i].isDigit()) {
            formattedString.append(input[i])
            digitCount++

            if ((digitCount == 2 || digitCount == 4) && i < currentValue.length) {
                newPosition++
            }

            if (digitCount == 2 || digitCount == 4) {
                formattedString.append('/')
            }
        }
    }

    return formattedString.toString() to newPosition
}

/*
*******************************************************
*           Preview: IdentityPreview                  *
*******************************************************
|  Description:                                       |
|  Fonction de prévisualisation pour l'écran de       |
|  saisie des informations d'identité lors de         |
|  l'inscription.                                     |
*******************************************************
*/
@Preview(showBackground = true, device = "id:Samsung S9+", showSystemUi = true)
@Composable
private fun IdentityPreview() {
    val navController = rememberNavController()
    MeetchiTheme{
        ScreenIdentity(navController = navController)
    }
}