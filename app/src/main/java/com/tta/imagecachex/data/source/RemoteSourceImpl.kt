package com.tta.imagecachex.data.source

import com.tta.imagecachex.data.api.ImageApiService
import com.tta.imagecachex.domain.RemoteSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteSourceImpl @Inject constructor(
    private val api: ImageApiService
) : RemoteSource {
    override fun getImageList() = flow {
        emit(api.getImageList())
    }

}