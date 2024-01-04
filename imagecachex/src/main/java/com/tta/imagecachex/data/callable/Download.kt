package com.tta.imagecachex.data.callable

import java.util.concurrent.Callable

abstract class Download<T> : Callable<T> {
    abstract fun download(url: String, imageViewWidth: Int, imageViewHeight: Int): T
}