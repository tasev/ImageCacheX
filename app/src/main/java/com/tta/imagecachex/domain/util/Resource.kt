package com.tta.imagecachex.domain.util

sealed class Resource<out O> {
    data class Success<out O>( val result: O) : Resource<O>()
    data class Failure( val errorMessage: String ) : Resource<Nothing>()
    data object Loading: Resource<Nothing>()
}