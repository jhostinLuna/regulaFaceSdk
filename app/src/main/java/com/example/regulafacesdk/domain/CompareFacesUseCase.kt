package com.example.regulafacesdk.domain

import android.graphics.Bitmap
import com.example.regulafacesdk.core.common.Resource
import com.example.regulafacesdk.core.failures.CoreFailure
import com.example.regulafacesdk.core.interactor.UseCase
import javax.inject.Inject

open class CompareFacesUseCase @Inject constructor(
    private val faceSdkRepository: FaceSdkRepository
): UseCase<CompareFacesUseCase.Params, Double>() {
    override suspend fun execute(parameters: Params): Resource<CoreFailure, Double> = faceSdkRepository.compareFaces(
        bitmapOne = parameters.bitmapOne,
        bitmapTwo = parameters.bitmapTwo
    )

    class Params(
        val bitmapOne: Bitmap,
        val bitmapTwo: Bitmap
    )
}
