package com.example.regulafacesdk.presentation

data class SimilarityUiState(
    val similarity: String = "0%",
    val isLoading: Boolean = false,
    val errorMessage: String? = ""
)
