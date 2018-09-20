package com.ggl.jr.cookbooksearchbyingredients.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream

class ImageHelper private constructor() {
    var resized: Bitmap? = null
    var filePath: String? = null
    var imageLoadedFromPicasso: ImageLoadedFromPicassoCallback? = null
    var errorCallback: ErrorCallback? = null

    private val picassoCallback = object : Callback {
        override fun onSuccess() {
            imageLoadedFromPicasso?.onImageLoaded()
        }

        override fun onError() {

        }
    }

    fun writeToFile(context: Context) {
        val dirPath = Environment.getExternalStorageDirectory().absolutePath + "/cookbookdir"
        val dir = File(dirPath)
        if (!dir.exists()) dir.mkdirs()

        val fileName = "recipe pic" + System.currentTimeMillis() + ".jpg"
        val file = File(dir, fileName)
        filePath = file.path

        var fOut: FileOutputStream? = null
        try {
            fOut = FileOutputStream(file)
            resized?.compress(Bitmap.CompressFormat.JPEG, 70, fOut)
            fOut.flush()
            MediaScannerConnection.scanFile(context, arrayOf(filePath), arrayOf("image/jpg"), null)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fOut?.close()
        }
    }

    fun getScaledImage(selectedImage: Uri, context: Context): Bitmap? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val whereArgs = arrayOf("image/jpg", "image/jpeg", "image/png", "image/webp", "image/gif")

        val where = (MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=? or " +
                MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?")

        val cursor = context.contentResolver.query(selectedImage, filePathColumn, where, whereArgs, null)
                ?: return null

        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            if (columnIndex < 0) return null

            val picturePath = cursor.getString(columnIndex)
            cursor.close()

            val resource = BitmapFactory.decodeFile(picturePath) ?: return null
            val originalWidth = resource.width.toFloat()
            val originalHeight = resource.height.toFloat()

            val currentMaxSideSize = Math.max(originalWidth, originalHeight)

            val scale = Math.min(1000 / currentMaxSideSize, 1f)

            resized = Bitmap.createScaledBitmap(resource, (originalWidth * scale).toInt(), (originalHeight * scale).toInt(), true)
            if (resource !== resized) resource.recycle()
        } else {
            errorCallback?.onImageLoadedError()
        }

        return resized
    }

    fun loadImageFromStorage(context: Context, filePath: String, imageView: ImageView) {
        Picasso.with(context)
                .load("file:// + $filePath")
                .fit()
                .centerCrop()
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(imageView, picassoCallback)
    }

    fun deleteFile(filePath: String) {
        val fileToDelete: File? = File(filePath)
        fileToDelete?.delete()
    }

    fun clear() {
        resized?.recycle()
        resized = null
        filePath = null
    }

    private object Holder {
        val INSTANCE = ImageHelper()
    }

    interface ImageLoadedFromPicassoCallback {
        fun onImageLoaded()
    }

    interface ErrorCallback {
        fun onImageLoadedError()
    }

    companion object {
        val instance: ImageHelper by lazy { Holder.INSTANCE }
    }
}