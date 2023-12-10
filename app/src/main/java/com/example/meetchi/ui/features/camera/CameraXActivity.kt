package com.example.meetchi.ui.features.camera

import androidx.compose.runtime.Composable
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
fun CameraXMainScreen() {
    val cameraPermissionState: PermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    CameraXMainContent(
        hasPermission = cameraPermissionState.status.isGranted,
        onRequestPermission = cameraPermissionState::launchPermissionRequest
    )
}

@Composable
fun CameraXMainContent(
    hasPermission: Boolean,
    onRequestPermission: () ->  Unit
) {
    if(hasPermission){
        CameraScreen()
    }else{
        NoPermissionScreen(onRequestPermission)
    }
}

@Preview(showBackground = true)
@Composable
fun CameraXPreview() {
    MeetchiTheme {
        CameraXMainScreen()
    }
}