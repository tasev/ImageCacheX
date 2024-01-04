package com.tta.imagecachex.data.api

import com.tta.imagecachex.data.model.ImageData
import retrofit2.Response
import retrofit2.http.GET

interface ImageApiService {

    @GET("image_list.json")
    suspend fun getImageList(): Response<List<ImageData>>

}