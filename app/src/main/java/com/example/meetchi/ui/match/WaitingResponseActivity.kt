package com.example.meetchi.ui.match

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.meetchi.ui.HomeActivity
import com.example.meetchi.ui.theme.MeetchiTheme
import com.example.meetchi.util.AnimationCancel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class WaitingResponseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetchiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val token = intent.getStringExtra("userToken")
                    if (token != null) {
                        WaitingResponseScreen(token)
                    }
                }
            }
        }
    }
}

fun sendNotification(tokenCible: String) {

    val client = OkHttpClient()
    val url = "https://fcm.googleapis.com/fcm/send"
    var json = JSONObject()
    var jsonNotification = JSONObject()
    jsonNotification.put("title","Meetchi")
    jsonNotification.put("body","Vous avez un Like")
    json.put("notification",jsonNotification)
    json.put("to",tokenCible)
    val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
    val body: RequestBody = RequestBody.create(JSON, json.toString())
    Log.d("Firestore:Log","${json}")
    val request = Request.Builder()
        .url(url)
        .post(body)
        .header("Authorization", "key=AAAAdKUpDsk:APA91bFxtUv_rz7o2-vnQDPD_0cJiE7qAYxgOMTsgOlCuwSDKUX17VgmGuyZkyAI8zeqIEHoWY-VyBTPG453bjUXwUHx_3nGMQ9wfmkRQM0jpxtlJYv1q1uaV8K9JoJZ67rdk7wzL8T5")
        .build()
    client.newCall(request).enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            val responseBody = response.body?.string()
            Log.d("Firestore:Log", "Réponse : $responseBody")

            try {
                val jsonResponse = JSONObject(responseBody)
                val results = jsonResponse.getJSONArray("results")
                val firstResult = results.getJSONObject(0)

                if (firstResult.has("error")) {
                    val error = firstResult.getString("error")
                    Log.e("Firestore:Log", "Erreur FCM : $error")
                    // Traitez l'erreur ici (par exemple, en mettant à jour le jeton d'enregistrement côté serveur)
                } else {
                    // Le message a été envoyé avec succès
                }
            } catch (e: JSONException) {
                Log.e("Firestore:Log", "Erreur lors de l'analyse de la réponse JSON : ${e.message}")
            }
        }

        override fun onFailure(call: Call, e: IOException) {
            // Gérez l'échec de la requête ici
            Log.e("Firestore:Log", "Échec de la requête : ${e.message}")
        }
    })
}

/*
*******************************************************
*       Function Composable: WaitingResponseScreen    *
*******************************************************
|  Description:                                       |
|  En cours de conception                             |
*******************************************************
*/
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WaitingResponseScreen(token: String) {
    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        val context = LocalContext.current
        var notifIsSend by remember{ mutableStateOf(false) }
        var time by remember{ mutableIntStateOf( 5 * 60 ) }
        if(!notifIsSend){
            sendNotification(token)
            notifIsSend = true
        }
        LaunchedEffect(true) {
            startTimer(time, onTick = { newTime ->
                time = newTime
            }, onFinish = {
                val intent = Intent(context, HomeActivity::class.java)
                context?.startActivity(intent, AnimationCancel.CancelAnimation(context))
            })
        }
        Text(text = "Waiting Response")
        Text(text = formatTime(time))
    }
}

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}
fun startTimer(
    initialTime: Int,
    onTick: (Int) -> Unit,
    onFinish: () -> Unit
) {
    var time = initialTime

    CoroutineScope(Dispatchers.Default).launch {
        while (time > 0) {
            delay(1000)
            time--
            onTick(time)
        }
        onFinish()
    }
}

/*
*******************************************************
*               Preview: ChatActivity                *
*******************************************************
|  Description:                                       |
|  En cours de conception                             |
*******************************************************
*/
@Preview(showBackground = true, device = "id:Samsung S9+", showSystemUi = true)
@Composable
fun WaitingResponseScreenPreview() {
    MeetchiTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            WaitingResponseScreen("")
        }
    }
}