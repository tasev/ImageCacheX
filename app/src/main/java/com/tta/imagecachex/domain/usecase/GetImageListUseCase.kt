package com.tta.imagecachex.domain.usecase

import com.tta.imagecachex.data.repository.RemoteRepositoryImpl
import com.tta.imagecachex.data.model.ImageData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

open class GetImageListUseCase @Inject constructor(
    private val repository: RemoteRepositoryImpl
) {

        suspend operator fun invoke(): Flow<Response<List<ImageData>>> {
            return repository.getImageList()
        }
}