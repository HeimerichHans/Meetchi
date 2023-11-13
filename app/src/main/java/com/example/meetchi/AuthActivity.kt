package com.example.meetchi

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
import androidx.compose.ui.unit.dp
import com.example.meetchi.ui.theme.AppTheme

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
private fun ButtonAuth(ButtonText: String , Image: Painter)
{
    Button(onClick = {},
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
                Text(text = ButtonText,
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
        ButtonAuth(stringResource(R.string.log_google), painterResource(R.drawable.icon_google))
        Divider(color = Color.Gray,
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .padding(horizontal = 30.dp))
        ButtonAuth(stringResource(R.string.log_facebook), painterResource(R.drawable.icon_facebook))
        Divider(color = Color.Gray,
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .padding(horizontal = 30.dp))
        ButtonAuth(stringResource(R.string.log_mail), painterResource(R.drawable.icon_mail))
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
    MeetchiTheme (appTheme = AppTheme.THEME_LIGHT){
        Auth()
    }
}