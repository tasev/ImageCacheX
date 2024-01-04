package com.tta.imagecachex.data.callable

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.tta.imagecachex.data.repository.ImageCacheRepositoryImpl
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.max
import kotlin.math.min


class DownloadImage(
    private val url: String,
    private val imageView: ImageView,
    private val cache: ImageCacheRepositoryImpl
) : Download<Bitmap?>() {

    override fun download(url: String, imageViewWidth: Int, imageViewHeight: Int): Bitmap? {
        var source: Bitmap? = null
        try {
            val conn: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
            val options = BitmapFactory.Options()
            options.inSampleSize = calculateInSampleSize(conn.contentLength)
            options.inJustDecodeBounds = false
            source = BitmapFactory.decodeStream(conn.inputStream, null, options)
            conn.disconnect()
            return source
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private val uiHandler = Handler(Looper.getMainLooper())

    override fun call(): Bitmap? {
        val bitmap = download(url, imageView.width, imageView.height)
        bitmap?.let {
            if (imageView.tag == url) {
                updateImageView(imageView, it)
            }
            cache.putBitmap(url, it)
        }
        return bitmap
    }

    private fun updateImageView(imageview: ImageView, bitmap: Bitmap) {
        uiHandler.post {
            imageview.setImageBitmap(bitmap)
        }
    }

    // prevent memory leaks
    private fun calculateInSampleSize(contentLength: Int): Int {
        val maxImageSize: Int = 400000
        var inSampleSize = 3

        if (contentLength > maxImageSize) {
            inSampleSize = max(inSampleSize, min(contentLength / maxImageSize, inSampleSize*10))
        }

        return inSampleSize
    }
}