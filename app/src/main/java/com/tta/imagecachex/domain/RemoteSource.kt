package com.tta.imagecachex.domain

import com.tta.imagecachex.data.model.ImageData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteSource {
    fun getImageList(): Flow<Response<List<ImageData>>>
}