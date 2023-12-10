package com.example.meetchi.ui.features.camera.photo_capture

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import com.example.meetchi.ui.theme.MeetchiTheme
import com.example.meetchi.util.StorageActivity


@Composable
fun CameraScreen() {
    MeetchiTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CameraContent()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun CameraContent() {
    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current
    val cameraController = remember {
        LifecycleCameraController(context).apply {
            bindToLifecycle(lifeCycleOwner)
            cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        }
    }

    // Declare a state to manage whether the image capture is complete
    var isCaptureComplete by remember { mutableStateOf(false) }

    // Declare a state to store the captured image
    var capturedImage by remember { mutableStateOf<ImageProxy?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp), // Optional padding for better visual appearance
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically, // Center vertically
            ) {
                if(!isCaptureComplete){
                    IconButton(
                        onClick = {
                            cameraController.takePicture(ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageCapturedCallback() {
                                override fun onCaptureSuccess(image: ImageProxy) {
                                    // Set the captured image in the state
                                    capturedImage = image
                                    // Mark the capture as complete
                                    isCaptureComplete = true
                                }

                                override fun onError(exception: ImageCaptureException) {
                                    // Handle error
                                }
                            })
                        },
                        modifier = Modifier.size(96.dp) // Adjust the size as needed
                    ) {
                        Box(
                            modifier = Modifier.size(96.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Adjust,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(96.dp) // Set the desired icon size
                            )
                        }
                    }
                }
            }
        }
    ) { _: PaddingValues ->
        if (isCaptureComplete) {
            // Display the captured image with validate and cancel buttons
            CapturedImagePreview(
                capturedImage = capturedImage?.toBitmap(),
                onValidate = {
                    // Return to CameraContent
                    //TODO a supprimer (peut etre)
                    isCaptureComplete = false
                },
                onCancel = {
                    // Return to CameraContent
                    isCaptureComplete = false
                },
                context = context
            )
        } else {
            // Display the camera preview
            AndroidView(factory = { context ->
                PreviewView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    scaleType = PreviewView.ScaleType.FILL_START
                }.also { previewView ->
                    previewView.controller = cameraController
                    cameraController.bindToLifecycle(lifeCycleOwner)
                }
            })
        }
    }
}


@Composable
private fun CapturedImagePreview(
    capturedImage: Bitmap?,
    onValidate: () -> Unit,
    onCancel: () -> Unit,
    context: Context
) {
    // Use the captured image to display it in a composable
    // You can use Image, Bitmap, or other appropriate components
    // For simplicity, I'm using a Box with a placeholder text
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (capturedImage != null) {
            // Display the captured image using Coil's Image composable
            val painter = rememberImagePainter(
                data = capturedImage
            )
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
            )
        } else {
            // Placeholder text when no image is captured
            Text(
                text = "No image captured",
            )
        }

        // Display validate and cancel buttons
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                // Cancel button
                IconButton(
                    onClick = onCancel,
                    modifier = Modifier
                        .size(96.dp) // Adjust the size as needed
                        .background(color = Color(0xFFFF875F), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cancel",
                        tint = Color.Black // Icon color
                    )
                }
                // Add a spacer to create space between the two icons
                Spacer(modifier = Modifier.width(48.dp))

                // Validate button
                IconButton(
                    onClick = {
                        if (capturedImage != null) {
                            // Instantiate StorageActivity and upload the image

                            val storageActivity = StorageActivity(capturedImage, context)
                            storageActivity.executeUpload()

                            // Perform any additional actions if needed
                            onValidate()
                        }
                              },
                    modifier = Modifier
                        .size(96.dp) // Adjust the size as needed
                        .background(color = Color(0xFF92D192), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Validate",
                        tint = Color.Black // Icon color
                    )
                }
            }
        }
    }
}
