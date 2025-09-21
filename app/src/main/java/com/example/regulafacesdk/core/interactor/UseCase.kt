package com.example.regulafacesdk.core.interactor

import com.example.regulafacesdk.core.common.Resource
import com.example.regulafacesdk.core.failures.CoreFailure

abstract class UseCase<in P, out R> where R : Any {
    suspend operator fun invoke(parameters: P, onResult: (Resource<CoreFailure, R>) -> Unit = {}) {
        onResult(execute(parameters))

    }
    abstract suspend fun execute(parameters: P): Resource<CoreFailure, R>
}