package com.tta.imagecachex.domain.util

class Constants {
    companion object {
        val maxMemory = Runtime.getRuntime().maxMemory() /1024
        val defaultCacheSize = (maxMemory/4).toInt()
    }
}