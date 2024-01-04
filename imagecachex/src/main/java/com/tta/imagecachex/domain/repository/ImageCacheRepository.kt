package com.tta.imagecachex.domain.repository

import android.graphics.Bitmap

interface ImageCacheRepository {
    fun putBitmap(url: String, bitmap: Bitmap)
    fun getBitmap(url: String): Bitmap?
    fun clear()
}