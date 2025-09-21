package com.example.regulafacesdk.presentation.features

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.regulafacesdk.core.common.Resource
import com.example.regulafacesdk.core.failures.CoreFailure
import com.example.regulafacesdk.domain.CompareFacesUseCase
import com.example.regulafacesdk.domain.GetImageUseCase
import com.example.regulafacesdk.presentation.PhotoUiState
import com.example.regulafacesdk.presentation.SimilarityUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getImageUseCase: GetImageUseCase,
    private val compareFacesUseCase: CompareFacesUseCase
): ViewModel() {
    private val _imageOneUiState: MutableStateFlow<PhotoUiState> = MutableStateFlow(PhotoUiState())
    private val _imageTwoUiState: MutableStateFlow<PhotoUiState> = MutableStateFlow(PhotoUiState())
    val imageOneUiState: StateFlow<PhotoUiState> = _imageOneUiState
    val imageTwoUiState: StateFlow<PhotoUiState> = _imageTwoUiState

    private val _similarityUiState: MutableStateFlow<SimilarityUiState> = MutableStateFlow(SimilarityUiState())
    val similarityUiState: StateFlow<SimilarityUiState> = _similarityUiState

    fun setBitmapImageOneUiState(bitmap: Bitmap) {
        _imageOneUiState.update {
            it.copy(
                bitmap = bitmap
            )
        }
    }
    fun setBitmapImageTwoUiState(bitmap: Bitmap) {
        _imageTwoUiState.update {
            it.copy(
                bitmap = bitmap
            )
        }

    }
    fun setImageUriImageOneUiState(imageUri: Uri) {
        viewModelScope.launch {
            _imageOneUiState.update {
                it.copy(
                    bitmap = getImage(imageUri)
                )
            }

        }
    }
    fun setImageUriImageTwoUiState(imageUri: Uri) {
        viewModelScope.launch {
            _imageTwoUiState.update {
                it.copy(
                    bitmap = getImage(imageUri)
                )
            }
        }
    }

    private suspend fun getImage(uri: Uri): Bitmap? {
        var vBitmap: Bitmap? = null
        this.getImageUseCase(
            parameters = GetImageUseCase.Params(uri),
            onResult = {
                vBitmap = it.data
            }
        )
        return vBitmap

    }
    fun compareFaces() {
        if (_imageOneUiState.value.bitmap == null || _imageTwoUiState.value.bitmap == null) {
            return
        }
        _similarityUiState.value = SimilarityUiState(isLoading = true)
        viewModelScope.launch {
            compareFacesUseCase(
                parameters = CompareFacesUseCase.Params(
                    bitmapOne = _imageOneUiState.value.bitmap!!,
                    bitmapTwo = _imageTwoUiState.value.bitmap!!
                )
            ) {resource ->
                when(resource) {
                    is Resource.Success -> {
                        Log.d("Similarity HomeViewModel", "Similarity: ${resource.data}")
                        resource.data?.let {
                            _similarityUiState.value = SimilarityUiState(
                                similarity = "${String.format(Locale.getDefault(),"%.2f", it * 100)}%"
                            )
                        }

                    }
                    is Resource.Error -> {
                        Log.d("Similarity HomeViewModel", "Error: ${resource.error}")
                        when(resource.error) {
                            is CoreFailure.FaceSdkError -> {
                                _similarityUiState.value = SimilarityUiState(
                                    errorMessage = resource.error.message
                                )
                            }
                            else -> {
                                _similarityUiState.value = SimilarityUiState(
                                    errorMessage = "Error desconocido"
                                )
                            }
                        }
                    }
                }
            }

        }
    }

    fun clear() {
        _imageOneUiState.value = PhotoUiState()
        _imageTwoUiState.value = PhotoUiState()
        _similarityUiState.value = SimilarityUiState()
    }


}