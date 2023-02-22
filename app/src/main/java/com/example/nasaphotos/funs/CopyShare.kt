package com.example.nasaphotos.funs

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.drawToBitmap
import androidx.core.widget.PopupWindowCompat
import com.example.nasaphotos.R
import com.example.nasaphotos.databinding.CopyShareBinding

object CopyShare {
    fun copyShare(ctx: Context, txt: String, act: Activity) {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popup = inflater.inflate(R.layout.copy_share, LinearLayout(ctx), false)
        val bnd = CopyShareBinding.bind(popup)
        val window = PopupWindow(popup,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true)
        window.elevation = 2f
        window.showAtLocation(popup, Gravity.CENTER, Gravity.CENTER, Gravity.CENTER)
        PopupWindowCompat.setWindowLayoutType(window,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)

        bnd.share.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, txt)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(ctx, shareIntent, null)
            window.dismiss()
        }
        bnd.copy.setOnClickListener {
            val clipboardManager =
                ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", txt)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(ctx, "Text copied", Toast.LENGTH_LONG).show()
            window.dismiss()
        }
        bnd.setW.setOnClickListener {
            val bitmap =
                act.window.decorView.rootView.drawToBitmap(android.graphics.Bitmap.Config.ARGB_8888)
            val wp = android.app.WallpaperManager.getInstance(ctx)
            wp.setBitmap(bitmap)
            Toast.makeText(ctx, "Wallpaper set", Toast.LENGTH_LONG).show()
            window.dismiss()
        }
    }
}