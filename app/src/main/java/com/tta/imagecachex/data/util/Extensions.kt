package com.tta.imagecachex.data.util

import com.tta.imagecachex.data.model.ImageData
import com.tta.imagecachex.data.model.ImageDataEntity

fun ImageData.toDbDataEntity() = ImageDataEntity(
    id = id,
    imageUrl = imageUrl,
)