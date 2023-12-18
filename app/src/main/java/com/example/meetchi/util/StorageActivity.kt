package com.example.meetchi.util

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

class StorageActivity(private val imageBitmap: Bitmap, private val context: Context) {

    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    private val imageRef: StorageReference by lazy {
        // Generate a unique filename
        val fileName = "image_${System.currentTimeMillis()}.jpg"
        storageRef.child("images/$fileName")
    }
    fun executeUpload() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val byteArray = bitmapToByteArray(imageBitmap)

                // Upload the image to Firebase Storage
                imageRef.putBytes(byteArray).await()
                Log.e("StorageActivity", "Image upload success!")
            } catch (exception: Exception) {
                // Handle the failure
                Log.e("StorageActivity", "Image upload failed!", exception)
            } finally {
                // Close the ImageProxy after using it
            }
        }
    }
    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()
    }

}