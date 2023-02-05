package com.example.beeruthus.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.*
import java.io.File

object ImageUtils {
    var job: Job? = null
    fun downloadImage(url: String, context: Context, fileName: String) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    job = CoroutineScope(Dispatchers.IO).launch {
                        val file = File(context.filesDir, fileName)
                        if (file.exists()) {
                            job?.cancel()
                        } else {
                            write(context, resource, file)
                        }
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
    }

    suspend fun write(context: Context, bitmap: Bitmap, file: File) =
        withContext(Dispatchers.IO) {
            try {
                val uri = Uri.fromFile(file)
                val resolver: ContentResolver = context.contentResolver
                resolver.openOutputStream(uri)?.use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                }
                job?.cancel()
            } catch (e: java.lang.Exception) {
                job?.cancel()
                println("could not open $bitmap")
            }
        }

    fun deleteImage(context: Context, fileName: String) {
        try {
            val file = File(context.filesDir, fileName)
            if (file.exists()) {
                file.delete()
            }
        } catch (e: java.lang.Exception) {
            println("could not open $e")
        }
    }
}