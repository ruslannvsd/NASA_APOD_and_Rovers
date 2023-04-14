package com.example.nasaphotos.funs

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.core.widget.PopupWindowCompat
import com.example.nasaphotos.R
import com.example.nasaphotos.databinding.TextWindowBinding

object TextWindow {
    fun textWindow(ctx: Context, txt: String) {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popup = inflater.inflate(R.layout.text_window, LinearLayout(ctx), false)
        val bnd = TextWindowBinding.bind(popup)
        val window = PopupWindow(popup,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true)
        window.elevation = 2f
        window.showAtLocation(popup, Gravity.CENTER, Gravity.CENTER, Gravity.CENTER)
        PopupWindowCompat.setWindowLayoutType(window,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)

        bnd.infoText.text = txt
    }
}