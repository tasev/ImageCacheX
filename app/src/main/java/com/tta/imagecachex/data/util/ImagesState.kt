package com.tta.imagecachex.data.util

import com.tta.imagecachex.data.model.ImageData

data class ImagesState(
    val images: List<ImageData> = listOf(),
    val isLoading: Boolean = true,
    val error: String = ""
)