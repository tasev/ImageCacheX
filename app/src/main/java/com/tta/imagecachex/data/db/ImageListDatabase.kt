package com.tta.imagecachex.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tta.imagecachex.data.model.ImageDataEntity

@Database(entities = [ImageDataEntity::class], version = 1, exportSchema = false)
abstract class ImageListDatabase : RoomDatabase() {
    abstract fun imageListDao(): ImageListDao
}