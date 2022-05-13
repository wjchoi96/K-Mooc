package com.programmers.kmooc.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.URL
import kotlin.concurrent.thread

object ImageLoader {

    fun loadImage(url: String, completed: (Bitmap?) -> Unit) {
        val handler = Handler(Looper.getMainLooper())
        if(getBitmap(url) != null){
            completed.invoke(getBitmap(url))
            return
        }

        thread(start = true) {
            var bitmap: Bitmap?
            try{
                bitmap = BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
                saveBitmap(bitmap)
            }catch (e: Exception){
                e.printStackTrace()
                bitmap = null
            }
            handler.post { completed.invoke(bitmap) }
        }
    }
    private fun saveBitmap(bitmap: Bitmap?){

    }
    private fun getBitmap(url: String): Bitmap? {
        return null
    }
}