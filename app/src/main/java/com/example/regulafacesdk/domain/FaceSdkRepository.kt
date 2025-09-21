package com.example.regulafacesdk.domain

import android.graphics.Bitmap
import com.example.regulafacesdk.core.common.Resource
import com.example.regulafacesdk.core.failures.CoreFailure

interface FaceSdkRepository {
    suspend fun compareFaces(bitmapOne: Bitmap, bitmapTwo: Bitmap): Resource<CoreFailure, Double>
}
