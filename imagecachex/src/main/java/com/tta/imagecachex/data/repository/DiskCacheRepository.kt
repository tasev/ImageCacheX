package com.tta.imagecachex.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.jakewharton.disklrucache.DiskLruCache
import com.tta.imagecachex.domain.repository.ImageCacheRepository
import java.io.*
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class DiskCacheRepository constructor(val context: Context) : ImageCacheRepository {

    private var cache: DiskLruCache = DiskLruCache.open(context.cacheDir, 1, 1, 100 * 1024 * 1024)

    private val INSTANCE: DiskCacheRepository? = null

    override fun getBitmap(url: String): Bitmap? {
        val key = convertUrlToKey(url)
        val snapshot: DiskLruCache.Snapshot? = cache.get(key)
        return if (snapshot != null) {
            val inputStream: InputStream = snapshot.getInputStream(0)
            val buffIn = BufferedInputStream(inputStream, 8 * 1024)
            BitmapFactory.decodeStream(buffIn)
        } else {
            null
        }
    }

    override fun putBitmap(url: String, bitmap: Bitmap) {
        val key = convertUrlToKey(url)

        var editor: DiskLruCache.Editor? = null
        try {
            editor = cache.edit(key)
            if (editor == null) {
                return
            }
            if (writeBitmapToFile(bitmap, editor)) {
                cache.flush()
                editor.commit()
            } else {
                editor.abort()
            }
        } catch (e: IOException) {
            try {
                editor?.abort()
            } catch (ignored: IOException) {
            }
        }
    }

    override fun clear() {
        cache.delete()
        cache = DiskLruCache.open(context.cacheDir, 1, 1, 100 * 1024 * 1024)

    }


    private fun writeBitmapToFile(bitmap: Bitmap, editor: DiskLruCache.Editor): Boolean {
        var out: OutputStream? = null
        try {
            out = BufferedOutputStream(editor.newOutputStream(0), 8 * 1024)
            return bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        } finally {
            out?.close()
        }
    }

    fun convertUrlToKey(url: String): String? {
        try {
            val md = MessageDigest.getInstance("MD5")
            val messageDigest = md.digest(url.toByteArray())
            val no = BigInteger(1, messageDigest)
            var hashtext = no.toString(16)
            while (hashtext.length < 32) {
                hashtext = "0$hashtext"
            }
            return hashtext

        } catch (e: NoSuchAlgorithmException) {
           return "-"
        }
    }


    @Synchronized
    fun getInstance(): DiskCacheRepository {
        return INSTANCE?.let { return INSTANCE } ?: run {
            return DiskCacheRepository(context)
        }
    }
}