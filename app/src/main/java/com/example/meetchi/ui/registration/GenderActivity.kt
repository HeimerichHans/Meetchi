package com.example.meetchi.ui.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.meetchi.R
import com.example.meetchi.ui.theme.MeetchiTheme
import com.example.meetchi.util.ScreenRegister

@Composable
fun ScreenGender(navController: NavController, modifier: Modifier = Modifier) {
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background)
    {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            Text(text = stringResource(id = R.string.genderAsk),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(horizontal = 30.dp))
            Spacer(modifier.height(50.dp))
            Button(onClick =
            {
                RegistrationActivity.user.genre = "1"
                navController.navigate(ScreenRegister.Description.route)
            }, modifier = Modifier
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
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = stringResource(id = R.string.genderBoy),
                            fontSize = 18.sp)
                    }
                }
                Column (horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center)
                {
                    Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null)
                }
            }
            Divider(color = Color.Gray,
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp))
            Button(onClick =
            {
                RegistrationActivity.user.genre = "2"
                navController.navigate(ScreenRegister.Description.route)
            }, modifier = Modifier
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
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = stringResource(id = R.string.genderGirl),
                            fontSize = 18.sp)
                    }
                }
                Column (horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center)
                {
                    Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null)
                }
            }
            Divider(color = Color.Gray,
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp))
            Button(onClick =
            {
                RegistrationActivity.user.genre = "3"
                navController.navigate(ScreenRegister.Description.route)
            }, modifier = Modifier
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
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = stringResource(id = R.string.genderOther),
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
    }
}

@Preview(showBackground = true)
@Composable
private fun GenderPreview() {
    val navController = rememberNavController()
    MeetchiTheme{
        ScreenGender(navController = navController)
    }
}

