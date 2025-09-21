package com.example.regulafacesdk.domain

import android.graphics.Bitmap
import android.net.Uri
import com.example.regulafacesdk.core.common.Resource
import com.example.regulafacesdk.core.failures.CoreFailure
import com.example.regulafacesdk.core.interactor.UseCase
import javax.inject.Inject

open class GetImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository
): UseCase<GetImageUseCase.Params, Bitmap>() {
    override suspend fun execute(parameters: Params): Resource<CoreFailure, Bitmap> = imageRepository.getImage(parameters.uri)

    class Params(
        val uri: Uri
    )

}
