package com.example.regulafacesdk.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import com.example.regulafacesdk.core.common.Resource
import com.example.regulafacesdk.core.failures.CoreFailure
import com.example.regulafacesdk.domain.FaceSdkRepository
import com.regula.facesdk.FaceSDK
import com.regula.facesdk.detection.request.OutputImageCrop
import com.regula.facesdk.detection.request.OutputImageParams
import com.regula.facesdk.enums.ImageType
import com.regula.facesdk.enums.OutputImageCropAspectRatio
import com.regula.facesdk.model.MatchFacesImage
import com.regula.facesdk.model.results.matchfaces.MatchFacesResponse
import com.regula.facesdk.model.results.matchfaces.MatchFacesSimilarityThresholdSplit
import com.regula.facesdk.request.MatchFacesRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FaceSdkRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val faceSDK: FaceSDK
): FaceSdkRepository {
    override suspend fun compareFaces(
        bitmapOne: Bitmap,
        bitmapTwo: Bitmap
    ): Resource<CoreFailure, Double> {
        return try {
            // Usamos suspendCoroutine para esperar el resultado del callback
            val similarity = suspendCoroutine { continuation ->
                val request = MatchFacesRequest(
                    listOf(
                        MatchFacesImage(bitmapOne, ImageType.PRINTED),
                        MatchFacesImage(bitmapTwo, ImageType.PRINTED),
                    )
                )
                val crop = OutputImageCrop(
                    OutputImageCropAspectRatio.OUTPUT_IMAGE_CROP_ASPECT_RATIO_3X4
                )
                val outputImageParams = OutputImageParams(crop, Color.WHITE)
                request.outputImageParams = outputImageParams

                faceSDK.matchFaces(context, request) { matchFacesResponse: MatchFacesResponse ->
                    // El callback del SDK se ejecuta aquí
                    val split = MatchFacesSimilarityThresholdSplit(matchFacesResponse.results, 0.75)
                    val result = if (split.matchedFaces.isNotEmpty()) {
                        Log.d("Similarity", "Similarity: ${split.matchedFaces[0].similarity}")
                        split.matchedFaces[0].similarity
                    } else if (split.unmatchedFaces.isNotEmpty()) {
                        Log.d("Similarity", "Similarity: ${split.unmatchedFaces[0].similarity}")
                        split.unmatchedFaces[0].similarity
                    } else {
                        Log.d("Similarity", "Similarity: null")
                        0.0
                    }
                    // Reanudamos la corrutina con el resultado
                    continuation.resume(result)
                }
            }
            Resource.Success(similarity)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(CoreFailure.FaceSdkError(message = e.message))
        }

    }

}