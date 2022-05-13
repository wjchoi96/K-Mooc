package com.programmers.kmooc.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import androidx.collection.LruCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.URL
import kotlin.concurrent.thread

object ImageLoader {
    private var memoryCache : LruCache<String, Bitmap>? = null
    private val handler = Handler(Looper.getMainLooper())
    fun loadImage(url: String, completed: (Bitmap?) -> Unit) {
        initCache()

        if(getBitmap(url) != null){
            completed.invoke(getBitmap(url))
            return
        }

        thread(start = true) {
            var bitmap: Bitmap?
            try{
                bitmap = BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
                saveBitmap(url, bitmap)
            }catch (e: Exception){
                e.printStackTrace()
                bitmap = null
            }
            handler.post { completed.invoke(bitmap) }
        }
    }
    private fun initCache(){
        if(memoryCache != null)
            return
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory/8
        memoryCache = object : LruCache<String, Bitmap>(cacheSize){
            override fun sizeOf(key: String, value: Bitmap): Int {
                return value.byteCount/1024
            }
        }
    }
    private fun saveBitmap(url: String, bitmap: Bitmap?){
        memoryCache?.put(url, bitmap ?: return)
    }
    private fun getBitmap(url: String): Bitmap? {
        return memoryCache!![url]
    }
}