package com.tta.imagecachex.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tta.imagecachex.data.util.Constants.IMAGE_LIST_TABLE_NAME

@Entity(tableName = IMAGE_LIST_TABLE_NAME)
data class ImageDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int? = 0,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?
)