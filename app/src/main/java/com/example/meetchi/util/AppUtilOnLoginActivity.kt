package com.example.meetchi.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meetchi.R
import com.example.meetchi.ui.login.AuthActivity
import com.example.meetchi.ui.theme.MeetchiTheme

/*
***************************************************************
*            Classe: AppUtilOnLoginActivity                   *
***************************************************************
|  Description:                                               |
|  Cette classe définit une activité utilitaire qui peut être |
|  utilisée dans le cadre de l'interface utilisateur lors de  |
|  l'authentification.                                        |
***************************************************************
*/
class AppUtilOnLoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetchiTheme {
            }
        }
    }
}

/*
***************************************************************
*             Fonction Composable: IconAuth                   *
***************************************************************
|  Description:                                               |
|  Cette fonction composable affiche l'icône de l'application |
|  avec le nom de l'application en dessous.                   |
***************************************************************
*/
@OptIn(ExperimentalTextApi::class)
@Composable
fun IconAuth()
{
    Column (horizontalAlignment = Alignment.CenterHorizontally)
    {
        val gradientColors = listOf(Color(0xFFFFC302), Color(0xFFFC6B2C))
        val image = painterResource(R.drawable.meetchi_app_icon)
        Image(
            modifier = Modifier.size(160.dp),
            painter = image,
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.app_name),
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            style = TextStyle(
                brush = Brush.verticalGradient(
                    colors = gradientColors
                )
            )
        )
    }
}

/*
**************************************************************
*            Fonction Composable: BackArrowAuth              *
**************************************************************
|  Description:                                              |
|  Cette fonction composable affiche une flèche de retour et |
|  permet de définir une action à exécuter lorsqu'elle est   |
|  cliquée.                                                  |
**************************************************************
*/
@Composable
public fun BackArrowAuth(context: Context)
{
    Row (
        modifier = Modifier.fillMaxSize()
    ){
        Icon(imageVector = Icons.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier.size(70.dp).padding(start = 25.dp, top = 25.dp).clickable { onIconClick(context) })
    }
}

/*
***************************************************************
*                  Fonction: onIconClick                      *
***************************************************************
|  Description:                                               |
|  Cette fonction définit l'action à exécuter lorsqu'on clique|
|  sur l'icône de retour.                                     |
***************************************************************
*/
fun onIconClick(context: Context) {
    val intent = Intent(context, AuthActivity::class.java)
    context.startActivity(intent, AnimationCancel.CancelAnimation(context))
}

/*
***************************************************************
*                Preview: IconAuthPreview                     *
***************************************************************
|  Description:                                               |
|  Fonction de prévisualisation pour l'affichage de           |
|  l'icône de l'application.                                  |
***************************************************************
*/
@Preview(showBackground = true)
@Composable
private fun IconAuthPreview() {
    MeetchiTheme (){
        IconAuth()
    }
}

/*
***************************************************************
*             Preview: BackArrowAuthPreview                   *
***************************************************************
|  Description:                                               |
|  Fonction de prévisualisation pour l'affichage de la        |
|  flèche de retour.                                          |
***************************************************************
*/
@Preview(showBackground = true)
@Composable
private fun BackArrowAuthPreview() {
    MeetchiTheme (){
        Surface (
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){
            BackArrowAuth(ComponentActivity())
        }

    }
}
