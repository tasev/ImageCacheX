package com.tta.imagecachex.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.tta.imagecachex.domain.repository.ImageCacheRepository

class ImageCacheRepositoryImpl (context: Context, newMaxSize: Int ): ImageCacheRepository {

    val diskCache = DiskCacheRepository(context).getInstance()
    val memoryCache = MemoryCacheRepository(newMaxSize)

    override fun putBitmap(url: String, bitmap: Bitmap) {
        memoryCache.putBitmap(url,bitmap)
        diskCache.putBitmap(url,bitmap)
    }

    override fun getBitmap(url: String): Bitmap? {
        return memoryCache.getBitmap(url)?:diskCache.getBitmap(url)
    }

    override fun clear() {
        memoryCache.clear()
        diskCache.clear()
    }
}