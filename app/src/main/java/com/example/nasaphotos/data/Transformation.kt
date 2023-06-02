package com.example.nasaphotos.data

import android.graphics.Bitmap
import com.squareup.picasso.Transformation

class ResizeTransformation(private val maxSize: Int) : Transformation {

    override fun key(): String {
        return "resizeTransformation(maxSize=$maxSize)"
    }

    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height

        if (width > maxSize || height > maxSize) {
            val newWidth = width / 4
            val newHeight = height / 4
            val returnBm = Bitmap.createScaledBitmap(source, newWidth, newHeight, false)
            //Toast.makeText(ctx, "Size is reduced", Toast.LENGTH_LONG).show()
            source.recycle()
            return returnBm
        }
        return source
    }
}
