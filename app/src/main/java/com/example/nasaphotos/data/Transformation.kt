package com.example.nasaphotos.data

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import com.squareup.picasso.Transformation

class ResizeTransformation(private val maxSize: Int, private val ctx: Context) : Transformation {

    override fun key(): String {
        return "resizeTransformation(maxSize=$maxSize)"
    }

    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height

        if (width > maxSize || height > maxSize) {
            val newWidth = width / 3
            val newHeight = height / 3
            val returnBm = Bitmap.createScaledBitmap(source, newWidth, newHeight, false)
            Toast.makeText(ctx, "Size is reduced", Toast.LENGTH_LONG).show()
            source.recycle()
            return returnBm
        }
        return source
    }
}
