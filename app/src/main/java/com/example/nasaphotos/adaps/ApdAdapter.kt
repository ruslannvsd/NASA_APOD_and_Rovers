package com.example.nasaphotos.adaps

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaphotos.R
import com.example.nasaphotos.data.ApodSimple
import com.example.nasaphotos.databinding.QuickViewBinding
import com.example.nasaphotos.funs.Cons
import com.example.nasaphotos.funs.Funs
import com.squareup.picasso.Picasso

class ApdAdapter(
    private val ctx: Context,
    private val infoShare: (String, String) -> Unit,
    private val list: List<ApodSimple>,
)
    : RecyclerView.Adapter<ApdAdapter.ApdViewHolder>() {
    class ApdViewHolder(val bnd: QuickViewBinding) : RecyclerView.ViewHolder(bnd.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApdViewHolder {
        val bnd = QuickViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApdViewHolder(bnd)
    }

    override fun onBindViewHolder(h: ApdViewHolder, p: Int) {
        val item = list[p]
        val im = h.bnd.image
        val src = item.url
        val text = h.bnd.date
        val hdUrl = if (item.hdurl == Cons.NONE) src else item.hdurl
        if (item.medType == Cons.IMAGE) {
            Picasso.get()
                .load(src)
                .placeholder(R.drawable.load)
                .into(im)
            text.text = item.date
        } else {
            val thumb = Funs.thumb(src)
            Picasso.get()
                .load(thumb)
                .placeholder(R.drawable.load)
                .into(im)
            ("VIDEO" + "\n" + item.date).also { text.text = it }
        }
        im.setOnClickListener {
            if (item.medType == Cons.IMAGE) {
                val info = item.title +
                        "\n" + item.date +
                        "\n" + item.expl
                infoShare(info, hdUrl)
                Toast.makeText(ctx, item.date, Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                val shareIntent = Intent.createChooser(intent, null)
                startActivity(ctx, shareIntent, null)
            }
        }
    }

    override fun getItemCount() = list.size
}