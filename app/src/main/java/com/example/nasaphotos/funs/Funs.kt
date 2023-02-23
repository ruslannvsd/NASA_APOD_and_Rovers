package com.example.nasaphotos.funs

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.nasaphotos.data.*


object Funs {
    fun newUrl(url: String): String {
        return if (url.startsWith("http://")) url.replace("http://", "https://") else url // http is not opened so I change it into https
    }

    fun wiFi(ctx: Context): Boolean { // checking is WiFi is connected
        var check = false
        val conMng = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = conMng.getNetworkCapabilities(conMng.activeNetwork)
        if (capabilities != null) {
            check = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }
        return check
    }

    fun thumb(url: String) : String { // checking if video is from Youtube or Vimeo
        val src: String
        val list1 = url.split("?")
        src = when {
            list1[0].contains("youtube") -> {
                val list2 = list1[0].split("/").reversed()
                val key = list2[0]
                "https://img.youtube.com/vi/$key/0.jpg"
            }
            list1[0].contains("vimeo") -> {
                val list2 = list1[0].split("/").reversed()
                val key = list2[0]
                "https://vumbnail.com/$key.jpg"
            }
            else -> {
                "https://vumbnail.com/21866269.jpg"
            }
        }
        return src
    }
    fun apodList(list: List<Apod>) : List<ApodSimple> {
        val photos = mutableListOf<ApodSimple>() // reconstruction of apod class into simpler one
        for (i in list) {
            val apod = apod(i)
            photos.add(apod)
        }
        return photos
    }
    private fun apod(p: Apod) : ApodSimple {
        return ApodSimple(p.date, p.expl, p.hdurl, p.medType, p.title, p.url)
    }
}