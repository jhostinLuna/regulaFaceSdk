package com.example.regulafacesdk.core.failures

sealed class CoreFailure {
    data class FileError(val code: Int = -1, val message: String? = "") : CoreFailure()
    data class FaceSdkError(val code: Int = -1, val message: String? = "") : CoreFailure()
}