package com.example.regulafacesdk.presentation

import android.graphics.Bitmap
import android.net.Uri

data class PhotoUiState (
    val bitmap: Bitmap? = null,
    val imageUri: Uri? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
    )
