package com.example.meetchi.ui.features.camera

import androidx.camera.core.CameraSelector
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.meetchi.ui.features.camera.no_permission.NoPermissionScreen
import com.example.meetchi.ui.features.camera.photo_capture.CameraScreen
import com.example.meetchi.ui.theme.MeetchiTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraXMainScreen(cameraSelected: CameraSelector) {
    val cameraPermissionState: PermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    CameraXMainContent(
        hasPermission = cameraPermissionState.status.isGranted,
        onRequestPermission = cameraPermissionState::launchPermissionRequest,
        cameraSelected = cameraSelected
    )
}

@Composable
fun CameraXMainContent(
    hasPermission: Boolean,
    onRequestPermission: () ->  Unit,
    cameraSelected:CameraSelector
) {
    if(hasPermission){
        CameraScreen(cameraSelected)
    }else{
        NoPermissionScreen(onRequestPermission)
    }
}

@Preview(showBackground = true, device = "id:Samsung S9+", showSystemUi = true)
@Composable
fun CameraXFrontPreview() {
    MeetchiTheme{
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CameraXMainScreen(CameraSelector.DEFAULT_FRONT_CAMERA)
        }
    }
}

@Preview(showBackground = true, device = "id:Samsung S9+", showSystemUi = true)
@Composable
fun CameraXBackPreview() {
    MeetchiTheme{
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CameraXMainScreen(CameraSelector.DEFAULT_BACK_CAMERA)
        }
    }
}