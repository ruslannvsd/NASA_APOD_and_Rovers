package com.example.nasaphotos.network

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.nasaphotos.R
import com.example.nasaphotos.adaps.ApdAdapter
import com.example.nasaphotos.data.*
import com.example.nasaphotos.funs.Cons
import com.example.nasaphotos.funs.Cons.BASE_URL
import com.example.nasaphotos.funs.Funs
import com.example.nasaphotos.funs.GsonJson
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiService {
    private val nasaApi: NasaApi

    init {
        val okkClient = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .client(okkClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        nasaApi = retrofit.create(NasaApi::class.java)
    }

    fun getPhotos(rover: String, sol: String) : LiveData<List<Photo>> {
        val photos = nasaApi.photos(rover, sol, Cons.API_KEY)
        val list = MutableLiveData<List<Photo>>()
        photos.enqueue(object : Callback<Photos> {
            override fun onResponse(call: Call<Photos>, response: Response<Photos>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        list.value = it.photos
                    }
                }
            }

            override fun onFailure(call: Call<Photos>, t: Throwable) {
            }
        })
        return list
    }

    fun getApd(
        listener: (String, String) -> Unit,
        rv: RecyclerView,
        ctx: Context
    ) {
        val apd = nasaApi.apd(Cons.COUNT, Cons.API_KEY)
        apd.enqueue(object : Callback<List<Apod>> {
            override fun onResponse(call: Call<List<Apod>>, response: Response<List<Apod>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val photos = Funs.apodList(it)
                        val endList = GsonJson(ctx).fromJson().ifEmpty { photos }
                        count(ctx, endList)
                        GsonJson(ctx).toJson(endList)
                        val ad = ApdAdapter(ctx, listener, endList)
                        rv.adapter = ad
                        rv.layoutManager =
                            StaggeredGridLayoutManager(
                                3, StaggeredGridLayoutManager.VERTICAL
                            )
                    }
                }
            }

            override fun onFailure(call: Call<List<Apod>>, t: Throwable) {
                Toast.makeText(ctx, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun today(listener: (String, String) -> Unit, im: ImageView, ctx: Context) {
        val today = nasaApi.today(Cons.API_KEY)
        today.enqueue(object : Callback<Apod> {
            override fun onResponse(call: Call<Apod>, response: Response<Apod>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val day = response.body()
                        day?.let { i: Apod ->
                            if (i.medType == Cons.IMAGE) {
                                Picasso.get().load(i.url).placeholder(R.drawable.load).into(im)

                            } else {
                                val thumb = Funs.thumb(i.url)
                                Picasso.get().load(thumb).placeholder(R.drawable.load).into(im)
                            }
                            val info = i.title +
                                    "\n" + i.date +
                                    "\n" + i.expl
                            im.setOnClickListener {
                                listener(info, i.hdurl)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Apod>, t: Throwable) {
                Toast.makeText(ctx, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun count (ctx: Context, l: List<ApodSimple>) {
        var imCount = 0
        var viCount = 0
        for (i in l) {
            if (i.medType == Cons.IMAGE) {
                imCount += 1
            } else viCount += 1
        }
        Toast.makeText(ctx, "$imCount ims & $viCount vis", Toast.LENGTH_LONG).show()
    }
}
