package com.tta.imagecachex.data.repository

import com.tta.imagecachex.data.db.ImageListDao
import com.tta.imagecachex.data.model.ImageDataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

open class LocalRepositoryImpl @Inject constructor(private val imageListDao: ImageListDao) {
    suspend fun addListItem(event: ImageDataEntity) = imageListDao.insert(event)
    fun getAllListItems(): Flow<List<ImageDataEntity>> =
        imageListDao.getAllItems().flowOn(Dispatchers.IO)
            .conflate()

    fun getSingleEventById(eventId: String): Flow<ImageDataEntity?> =
        imageListDao.getSingleImageById(eventId).flowOn(Dispatchers.IO)
            .conflate()

    suspend fun deleteAllData() =
        imageListDao.deleteAll()

    suspend fun addAllListItems(events: List<ImageDataEntity>) =
        imageListDao.saveAllItems(events)

}