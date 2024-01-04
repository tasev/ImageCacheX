package com.tta.imagecachex.data.repository

import android.graphics.Bitmap
import android.util.Log
import android.util.LruCache
import com.tta.imagecachex.domain.repository.ImageCacheRepository
import com.tta.imagecachex.domain.util.Constants

class MemoryCacheRepository(newMaxSize: Int) : ImageCacheRepository {

    private val cache: LruCache<String, Bitmap>

    init {
        var cacheSize: Int
        if (newMaxSize > Constants.maxMemory) {
            cacheSize = Constants.defaultCacheSize
            Log.d(
                "memory_cache",
                "New value of cache is bigger than maximum cache available on system"
            )
        } else {
            cacheSize = newMaxSize
        }
        cache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, value: Bitmap): Int {
                return (value.rowBytes) * (value.height) / 1024
            }
        }
    }

    override fun putBitmap(url: String, bitmap: Bitmap) {
        cache.put(url, bitmap)
    }

    override fun getBitmap(url: String): Bitmap? {
        return cache.get(url)
    }

    override fun clear() {
        cache.evictAll()
    }


}