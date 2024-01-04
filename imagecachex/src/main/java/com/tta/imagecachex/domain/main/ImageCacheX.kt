package com.tta.imagecachex.domain.main

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.tta.imagecachex.data.callable.Download
import com.tta.imagecachex.data.callable.DownloadImage
import com.tta.imagecachex.data.repository.ImageCacheRepositoryImpl
import com.tta.imagecachex.domain.util.Constants
import java.util.concurrent.Executors
import java.util.concurrent.Future

class ImageCacheX private constructor(context: Context, cacheSize: Int) {
    private val cache = ImageCacheRepositoryImpl(context, cacheSize)
    private val executorService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
    private val mRunningDownloadList: HashMap<String, Future<Bitmap?>> = hashMapOf()


    fun displayImage(url: String, imageview: ImageView, placeholder: Int?) {
        var bitmap = cache.getBitmap(url)
        bitmap?.let {
            imageview.setImageBitmap(it)
            return
        }
            ?: run {
                imageview.tag = url
                if (placeholder != null)
                    imageview.setImageResource(placeholder)
                addDownloadImageTask(url, DownloadImage(url, imageview, cache))
            }

    }


    fun addDownloadImageTask(url: String, downloadTask: Download<Bitmap?>) {
        mRunningDownloadList.put(url, executorService.submit(downloadTask))
    }


    fun clearCache() {
        cache.clear()
    }

    fun cancelTask(url: String) {
        synchronized(this) {
            mRunningDownloadList.forEach {
                if (it.key == url && !it.value.isDone)
                    it.value.cancel(true)
            }
        }
    }

    fun cancelAll() {
        synchronized(this) {
            mRunningDownloadList.forEach {
                if (!it.value.isDone)
                    it.value.cancel(true)
            }
            mRunningDownloadList.clear()
        }
    }


    companion object {
        private val INSTANCE: ImageCacheX? = null

        @Synchronized
        fun getInstance(
            context: Context,
            cacheSize: Int = Constants.defaultCacheSize
        ): ImageCacheX {
            return INSTANCE?.let { return INSTANCE }
                ?: run {
                    return ImageCacheX(context, cacheSize)
                }
        }
    }
}