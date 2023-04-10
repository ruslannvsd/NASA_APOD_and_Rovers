package com.example.nasaphotos.adaps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaphotos.R
import com.example.nasaphotos.data.Photo
import com.example.nasaphotos.databinding.ListItemBinding
import com.example.nasaphotos.funs.Funs
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class RoverPhotosAdapter(
    private val dimens: Int,
    private val infoShare: (String, String) -> Unit,
    private val photos: List<Photo>
) : RecyclerView.Adapter<RoverPhotosAdapter.ListViewHolder>() {
    class ListViewHolder(val bnd: ListItemBinding) : RecyclerView.ViewHolder(bnd.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val bnd = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(bnd)
    }

    override fun onBindViewHolder(h: ListViewHolder, p: Int) {
        val im = h.bnd.image
        val photo = photos[p]
        val url = photo.src
        val src = Funs.newUrl(url)

        Picasso.get()
            .load(src)
            .placeholder(R.drawable.load)
            .into(im, object : Callback {
                override fun onSuccess() {
                    val bm = im.drawable
                    val wid = bm.intrinsicWidth
                    val hei = bm.intrinsicHeight
                    "${hei}x$wid".also { h.bnd.size.text = it }
                    if (hei < dimens) h.bnd.card.visibility = View.GONE // make hei variable
                }
                override fun onError(e: Exception?) {
                    e?.printStackTrace()
                }
            })

        im.setOnClickListener {
            val info = info(photo)
            infoShare(
                info,
                src
            )
        }
    }

    override fun getItemCount() = photos.size

    private fun info(photo: Photo) : String {
        return photo.rover.name + "\n" +
                "Sol: ${photo.sol}" + "\n" +
                "Date: ${photo.earthDate}" + "\n" +
                "#${photo.id}-${photo.camera.name.lowercase()}"
    }
}