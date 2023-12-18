package com.example.meetchi.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.meetchi.ui.theme.AppTheme
import com.example.meetchi.ui.theme.MeetchiTheme
import androidx.compose.ui.platform.LocalContext

/*
*******************************************************
*               Activity: ColorTest                   *
*******************************************************
|  Description:                                       |
|  Activité  pour tester les thèmes de                |
|  couleurs.                                          |
*******************************************************
*/
class ColorTest : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            MeetchiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ColorTest("Android", context = context)
                }
            }
        }
    }
}

/*
*******************************************************
*       Fonction Composable: ColorTest                *
*******************************************************
|  Description:                                       |
|  Compose l'écran de test des couleurs et permet     |
|  à l'utilisateur de choisir entre les thèmes        |
|  "dark", "light", et "standard".                    |
*******************************************************
*/
@Composable
fun ColorTest(name: String, modifier: Modifier = Modifier, context: Context) {
    val sharedPreferences = context.getApplicationContext().getSharedPreferences("Parameter", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Button(onClick = {
            editor.putString("cleCouleur", "dark")
            editor.apply()
        }) {}
        Button(onClick = {
            editor.putString("cleCouleur", "light")
            editor.apply()
        }) {}
        Button(onClick = {
            editor.putString("cleCouleur", "standard")
            editor.apply()
        }) {}
    }
}

/*
*******************************************************
*           Preview: ColorTestPreview                 *
*******************************************************
|  Description:                                       |
|  Compose la prévisualisation de l'écran de test des |
|  couleurs en fonction du thème enregistré dans les  |
|  préférences partagées.                             |
*******************************************************
*/
@Preview(showBackground = true, device = "id:Samsung S9+", showSystemUi = true)
@Composable
fun ColorTestPreview() {
    val context = LocalContext.current
    val sharedPreferences = context.getApplicationContext().getSharedPreferences("Parameter", Context.MODE_PRIVATE)
    val theme = sharedPreferences.getString("cleCouleur", "")
    val appTheme = when (theme) {
        "dark" -> AppTheme.THEME_DARK
        "light" -> AppTheme.THEME_LIGHT
        "standard" -> AppTheme.THEME_STANDARD
        else -> AppTheme.THEME_STANDARD // Thème par défaut si la chaîne ne correspond à aucun cas
    }
    MeetchiTheme (appTheme = appTheme){
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ColorTest("Android", context = context)
        }
    }
}