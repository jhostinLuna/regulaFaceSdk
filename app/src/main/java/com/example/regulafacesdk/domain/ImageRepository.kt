package com.example.regulafacesdk.domain

import android.graphics.Bitmap
import android.net.Uri
import com.example.regulafacesdk.core.common.Resource
import com.example.regulafacesdk.core.failures.CoreFailure

interface ImageRepository {

    suspend fun getImage(uri: Uri): Resource<CoreFailure, Bitmap>
}
