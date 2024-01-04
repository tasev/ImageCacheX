package com.tta.imagecachex.data.api

import okhttp3.Interceptor
import okhttp3.Response

class ImageApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url.newBuilder()
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}