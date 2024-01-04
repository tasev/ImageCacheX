package com.tta.imagecachex.data.model

import com.google.gson.annotations.SerializedName

data class ImageData(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("imageUrl")
    val imageUrl: String? = ""
)