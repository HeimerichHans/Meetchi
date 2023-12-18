package com.example.meetchi.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.meetchi.MainActivity
import com.example.meetchi.R
import com.example.meetchi.model.Exclude
import com.example.meetchi.model.User
import com.example.meetchi.ui.login.RegisterMailActivity
import com.example.meetchi.ui.match.WaitingResponseActivity
import com.example.meetchi.ui.theme.MeetchiTheme
import com.example.meetchi.util.AccountCheckerReadyActivity
import com.example.meetchi.util.AnimationCancel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import java.util.Calendar
import kotlin.math.roundToInt

/*
*******************************************************
*               Fragment: SwipeScreen                 *
*******************************************************
|  Description:                                       |
|  En cours de conception                             |
*******************************************************
*/
@Composable
fun SwipeScreen(userDB : MutableState<User>, listSwipe: MutableState<ArrayList<User>>) {
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        Column (
            modifier = Modifier.fillMaxSize()
        ){
            Row (
                horizontalArrangement = Arrangement.Start
            ){
                val gradientColors = listOf(Color(0xFFFFC302), Color(0xFFFC6B2C))
                Text(
                    modifier = Modifier.padding(start = 5.dp, top = 5.dp),
                    text = stringResource(R.string.app_name),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    style = TextStyle(
                        brush = Brush.verticalGradient(
                            colors = gradientColors
                        )
                    )
                )
            }
            Box()
            {
                val context = LocalContext.current
                var excludeSwitch by remember { mutableStateOf(Exclude()) }
                val screenWidth = LocalConfiguration.current.screenWidthDp.dp.value
                var offsetX by remember { mutableFloatStateOf(0f) }
                var offsetY by remember { mutableFloatStateOf(0f) }
                var refreshLoadExcludeSwipeList by remember { mutableStateOf(false) }
                var refreshLoadSwipeList by remember { mutableStateOf(false) }
                var forceRecompose by remember { mutableStateOf(false) }
                var isDrag by remember { mutableStateOf(false) }
                var excludeSwipe by remember { mutableStateOf(false) }
                var userForExclusion by remember { mutableStateOf(User()) }
                LaunchedEffect(forceRecompose){
                    if(forceRecompose){
                        val forceRecomposeUser = User()
                        listSwipe.value.add(forceRecomposeUser)
                        listSwipe.value.remove(forceRecomposeUser)
                        forceRecompose = false
                    }
                }
                LaunchedEffect(excludeSwipe) {
                    if(excludeSwipe){
                        val database = Firebase.firestore
                        database.collection("Exclude")
                            .get()
                            .addOnSuccessListener { documents->
                                for(document in documents){
                                    val uid = document.toObject<Exclude>()
                                    excludeSwitch = uid
                                }
                                userForExclusion.uid?.let { excludeSwitch.insert_uid(it) }
                                database.collection("Exclude")
                                    .document(FirebaseAuth.getInstance().uid.toString())
                                    .set(excludeSwitch)
                                    .addOnSuccessListener {
                                        Log.d("Firestore:Log", "DocumentSnapshot added")
                                        listSwipe.value.remove(listSwipe.value[0])
                                        offsetX = 0f
                                        offsetY = 0f
                                        excludeSwipe = false
                                    }
                            }
                    }
                }
                if(listSwipe.value.size >= 2){
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.Gray),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom = 10.dp)
                    )
                    {
                        Column {
                            Row ( horizontalArrangement = Arrangement.SpaceBetween ){
                                Text( modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 30.dp, top = 25.dp),
                                    text = listSwipe.value[1].pseudonyme.toString(),
                                )
                                val calendar = Calendar.getInstance()
                                calendar.time = listSwipe.value[1].dateNaissance!!
                                val age = Calendar.getInstance().get(Calendar.YEAR) - calendar.get(Calendar.YEAR)
                                Text( modifier = Modifier.padding(end = 30.dp, top = 25.dp),
                                    text = age.toString()
                                )
                            }
                            Text( modifier = Modifier.padding(start = 30.dp, top = 45.dp),
                                text = listSwipe.value[1].description.toString()
                            )
                        }
                    }
                }
                if(listSwipe.value.size >= 1){
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom = 10.dp)
                            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDragStart = {
                                        isDrag = true
                                    },
                                    onDragEnd = {
                                        isDrag = false
                                    }
                                )
                                { change, dragAmount ->
                                    change.consume()
                                    offsetX += dragAmount.x
                                    offsetY += dragAmount.y
                                }
                            }
                            .graphicsLayer(rotationZ = offsetX / 20)
                    )
                    {
                        Column {
                            Row ( horizontalArrangement = Arrangement.SpaceBetween ){
                                Text( modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 30.dp, top = 25.dp),
                                    text = listSwipe.value[0].pseudonyme.toString()
                                )
                                val calendar = Calendar.getInstance()
                                calendar.time = listSwipe.value[0].dateNaissance!!
                                val age =  Calendar.getInstance().get(Calendar.YEAR) - calendar.get(Calendar.YEAR)
                                Text( modifier = Modifier.padding(end = 30.dp, top = 25.dp),
                                    text = age.toString()
                                )
                            }
                            Text( modifier = Modifier.padding(start = 30.dp, top = 45.dp),
                                text = listSwipe.value[0].description.toString()
                            )
                        }
                    }
                }
                if(listSwipe.value.isEmpty()){
                    Column ( modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){
                        Button(onClick =
                            {
                                refreshLoadExcludeSwipeList = true
                            })
                        {
                            Text(text = stringResource(R.string.newCards))
                        }
                    }
                }
                LaunchedEffect(offsetX) {
                    if(!isDrag && offsetX > screenWidth ){
                        offsetX += 50
                    } else if (!isDrag && offsetX < -screenWidth){
                        offsetX -= 50
                    } else if(!isDrag && offsetX > 50){
                        offsetX -= 50
                    } else if(!isDrag && offsetX < - 50){
                        offsetX += 50
                    } else if(!isDrag && (offsetX < 50 || offsetX > -50) && offsetX != 0F){
                        offsetX = 0F
                    }
                    if(!isDrag && offsetX > 5 * screenWidth ){

                        offsetX = 0f
                        offsetY = 0f
                        val intent = Intent(context, WaitingResponseActivity::class.java)
                        intent.putExtra("userToken","${listSwipe.value[0].token}")
                        listSwipe.value.remove(listSwipe.value[0])
                        context?.startActivity(intent, AnimationCancel.CancelAnimation(context))
                    }
                    if(!isDrag && offsetX < 5 * -screenWidth && !excludeSwipe){
                        userForExclusion = listSwipe.value[0]
                        excludeSwipe = true
                    }
                }
                LaunchedEffect(offsetY) {
                    if(!isDrag && offsetY > 50){
                        offsetY -= 50
                    } else if(!isDrag && offsetY < -50 ){
                        offsetY += 50
                    } else if(!isDrag && (offsetY < 50 || offsetY > -50) && offsetY != 0F){
                        offsetY = 0F
                    }
                }
                LaunchedEffect(refreshLoadExcludeSwipeList){
                    if(refreshLoadExcludeSwipeList){
                        val database = Firebase.firestore
                        database.collection("Exclude")
                            .get()
                            .addOnSuccessListener { documents->
                                for(document in documents){
                                    val uid = document.toObject<Exclude>()
                                    excludeSwitch = uid
                                }
                                refreshLoadSwipeList = true
                            }
                        refreshLoadExcludeSwipeList = false
                    }
                }
                LaunchedEffect(refreshLoadSwipeList){
                    if(refreshLoadSwipeList){
                        val database = Firebase.firestore
                        database.collection("User")
                            .whereEqualTo("account_ready",true)
                            .whereEqualTo("genre",getGenderSearch(userDB.value))
                            .limit(100)
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    if(document.id != FirebaseAuth.getInstance().uid.toString() && !excludeSwitch.contains_uid(document.id)){
                                        val user = document.toObject<User>()
                                        listSwipe.value.add(user)
                                    }
                                }
                                forceRecompose = true
                            }
                            .addOnFailureListener { exception ->
                                Log.w("Firestore:Log", "Error getting documents: ", exception)
                            }
                        refreshLoadSwipeList = false
                    }
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState")
@Preview(showBackground = true, device = "id:Samsung S9+", showSystemUi = true)
@Composable
fun SwipePreview() {
    val listSwipe by remember { mutableStateOf(ArrayList<User>()) }
    val user by remember { mutableStateOf(User("Toto",
        true,
        Calendar.getInstance().time,
        Calendar.getInstance().time,
        Calendar.getInstance().time,
        "Toto",
        "Toto","Toto","Toto",
        "1")) }
    listSwipe.add(
        User("Toto",
            true,
            Calendar.getInstance().time,
            Calendar.getInstance().time,
            Calendar.getInstance().time,
            "Toto",
            "Toto","Toto","Toto",
            "2")
    )
    listSwipe.add(
        User("Toto",
            true,
            Calendar.getInstance().time,
            Calendar.getInstance().time,
            Calendar.getInstance().time,
            "Toto",
            "Toto","Toto","",
            "2")
    )
    MeetchiTheme {
        SwipeScreen(mutableStateOf(user),mutableStateOf(listSwipe))
    }
}

@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState")
@Preview(showBackground = true, device = "id:Samsung S9+", showSystemUi = true)
@Composable
fun SwipeVidePreview() {
    val listSwipe by remember { mutableStateOf(ArrayList<User>()) }
    val user by remember { mutableStateOf(User("Toto",
        true,
        Calendar.getInstance().time,
        Calendar.getInstance().time,
        Calendar.getInstance().time,
        "Toto",
        "Toto","Toto","Toto",
        "1")) }
    MeetchiTheme {
        SwipeScreen(mutableStateOf(user),mutableStateOf(listSwipe))
    }
}