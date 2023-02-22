package com.example.nasaphotos.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.nasaphotos.data.Photo

class PhotosViewModel : ViewModel() {
    private val rep = ApiService()
    fun photosViewModel (rover: String, sol: String) : LiveData<List<Photo>> {
        return rep.getPhotos(rover, sol)
    }
}