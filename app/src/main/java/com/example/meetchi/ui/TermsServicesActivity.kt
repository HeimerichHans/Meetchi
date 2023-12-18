package com.example.meetchi.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.meetchi.R
import com.example.meetchi.ui.theme.MeetchiTheme
import com.example.meetchi.util.BackArrowTerms
import java.io.BufferedReader
import java.io.InputStreamReader


    class TermsServicesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetchiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color =  MaterialTheme.colorScheme.background
                ) {
                    TermsServicesContent(context = this)
                }
            }
        }
    }
}

@Composable
fun TermsServicesContent(context: Context, modifier: Modifier = Modifier) {
    val termsAndConditionsText = readTermsAndConditionsText(context)
    Column {
        BackArrowTerms(context = LocalContext.current)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = termsAndConditionsText,
                modifier = modifier
            )
        }
    }
}

fun readTermsAndConditionsText(context: Context): String {
    val inputStream = context.resources.openRawResource(R.raw.terms_services)
    val reader = BufferedReader(InputStreamReader(inputStream))
    val stringBuilder = StringBuilder()
    var line: String?
    try {
        while (reader.readLine().also { line = it } != null) {
            stringBuilder.append(line).append('\n')
        }
    } catch (e: Exception) {
        // Handle the exception
    } finally {
        try {
            inputStream.close()
        } catch (e: Exception) {
            // Handle the exception
        }
    }
    return stringBuilder.toString()
}

@Preview(showBackground = true, device = "id:Samsung S9+", showSystemUi = true)
@Composable
fun TermsServicesPreview() {
    MeetchiTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color =  MaterialTheme.colorScheme.background
        ) {
            TermsServicesContent(context = LocalContext.current)
        }
    }
}