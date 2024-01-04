package com.tta.imagecachex.data.repository

import com.tta.imagecachex.data.source.RemoteSourceImpl
import com.tta.imagecachex.domain.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val remoteSource: RemoteSourceImpl
) : RemoteRepository {
    override fun getImageList() = remoteSource.getImageList()
}