package com.tta.imagecachex.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tta.imagecachex.data.model.ImageDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageListDao {
    @Query(value = "SELECT * from image_list_table_name ORDER BY id ASC")
    fun getAllItems(): Flow<List<ImageDataEntity>>

    @Query("SELECT * FROM image_list_table_name WHERE id=:id ")
    fun getSingleImageById(id: String): Flow<ImageDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllItems(event: List<ImageDataEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: ImageDataEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(event: ImageDataEntity)

    @Query("DELETE from image_list_table_name")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteItemEntity(event: ImageDataEntity)
}
