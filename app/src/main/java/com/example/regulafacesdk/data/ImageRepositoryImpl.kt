package com.example.regulafacesdk.data

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.example.regulafacesdk.core.common.Resource
import com.example.regulafacesdk.core.failures.CoreFailure
import com.example.regulafacesdk.domain.ImageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): ImageRepository {
    override suspend fun getImage(uri: Uri): Resource<CoreFailure, Bitmap> {
        return try {
            var bitmap: Bitmap? = null
            // Usa el ContentResolver para abrir un stream de datos desde la URI
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                // Decodifica el stream de datos en un Bitmap
                bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)


            }
            Resource.Success(bitmap)
        } catch (exception: Exception) {
            Resource.Error(CoreFailure.FileError(message = exception.message))
        }
    }
}