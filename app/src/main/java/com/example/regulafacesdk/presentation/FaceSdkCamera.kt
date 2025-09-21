package com.example.regulafacesdk.presentation

import android.content.Context
import com.example.regulafacesdk.MainActivity
import com.regula.facesdk.FaceSDK
import com.regula.facesdk.configuration.FaceCaptureConfiguration
import com.regula.facesdk.model.results.FaceCaptureResponse
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FaceSdkCamera constructor(
    private val context: Context,
    private val faceSdk: FaceSDK,
    var imageCaptureListener: ImageCaptureListener
) {
    fun presentFaceCapture() {
        val configuration = FaceCaptureConfiguration.Builder().setCameraSwitchEnabled(true).build()
        faceSdk.presentFaceCaptureActivity(context,configuration){ faceCaptureResponse: FaceCaptureResponse? ->

            faceCaptureResponse?.let { response ->
                imageCaptureListener.getBitmap(response.image.bitmap)
            }
        }
    }
}