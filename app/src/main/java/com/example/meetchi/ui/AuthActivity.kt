package com.example.meetchi.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.sp
import com.example.meetchi.ui.theme.MeetchiTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.meetchi.MainActivity
import com.example.meetchi.R
import com.example.meetchi.ui.theme.AppTheme
import com.example.meetchi.util.FacebookAuthActivity
import com.example.meetchi.util.enumUtil
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetchiTheme {
                Auth()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 13 && resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        MainActivity.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("UserStatus","Connection Success")
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }

    @Composable
    fun Auth(modifier: Modifier = Modifier) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        )
        {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement  = Arrangement.Bottom
            )
            {
                IconAuthPreview()
                Spacer(modifier.height(100.dp))
                Text(text = stringResource(R.string.ask_how_log),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(horizontal = 30.dp))
                ListButtonAuth()
                InscriptionAuth()
            }
        }
    }

    @OptIn(ExperimentalTextApi::class)
    @Composable
    private fun IconAuth()
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

    @Composable
    private fun ButtonAuth(type: enumUtil.LoginType , Image: Painter)
    {
        var TextButton = ""
        when(type){
            enumUtil.LoginType.Google_type -> {
                TextButton = stringResource(R.string.log_google)
            }
            enumUtil.LoginType.Facebook_type -> {
                TextButton = stringResource(R.string.log_facebook)
            }
            enumUtil.LoginType.Mail_type -> {
                TextButton = stringResource(R.string.log_mail)
            }
        }
        val context = LocalContext.current
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(stringResource(id = R.string.client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context,gso)
        Button(onClick = {
            when(type){
                enumUtil.LoginType.Google_type -> {
                    ActivityCompat.startActivityForResult(this,googleSignInClient.signInIntent, 13,null)
                }
                enumUtil.LoginType.Facebook_type -> {
                    intent = Intent(this, FacebookAuthActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    finish()
                }
                enumUtil.LoginType.Mail_type -> {

                }
            }
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 5.dp)
                .size(60.dp),
            shape = MaterialTheme.shapes.small.copy(all = ZeroCornerSize),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background))
        {
            Column (horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(0.8f))
            {
                Row (verticalAlignment = Alignment.CenterVertically){
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = Image,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = TextButton,
                        fontSize = 18.sp)
                }
            }
            Column (horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center)
            {
                Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null)
            }
        }
    }



    @Composable
    private fun ListButtonAuth(){
        Column {
            ButtonAuth(enumUtil.LoginType.Google_type, painterResource(R.drawable.icon_google))
            Divider(color = Color.Gray,
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp))
            ButtonAuth(enumUtil.LoginType.Facebook_type, painterResource(R.drawable.icon_facebook))
            Divider(color = Color.Gray,
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp))
            ButtonAuth(enumUtil.LoginType.Mail_type, painterResource(R.drawable.icon_mail))
        }
    }

    @Composable
    fun InscriptionAuth()
    {
        Column (modifier = Modifier
            .padding(30.dp),
            horizontalAlignment = Alignment.Start)
        {
            Text(text = "Pas encore membre ?",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .height(30.dp)
                    .fillMaxSize())
            TextButton(onClick = {})
            {
                Text("Inscription")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun InscriptionAuthPreview() {
        MeetchiTheme {
            InscriptionAuth()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ListButtonAuthPreview() {
        MeetchiTheme {
            ListButtonAuth()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun IconAuthPreview() {
        MeetchiTheme {
            IconAuth()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun AuthPreview() {
        MeetchiTheme (appTheme = AppTheme.THEME_STANDARD){
            Auth()
        }
    }
}

