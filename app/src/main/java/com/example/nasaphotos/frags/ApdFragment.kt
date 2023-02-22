package com.example.nasaphotos.frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.nasaphotos.databinding.FragmentApdBinding
import com.example.nasaphotos.funs.GsonJson
import com.example.nasaphotos.network.ApiService
import java.io.File

class ApdFragment : Fragment() {
    private lateinit var bnd: FragmentApdBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    GsonJson(requireContext()).sharedPref.edit().clear().apply()
                    findNavController().popBackStack()
                }
            })
        bnd = FragmentApdBinding.inflate(inflater, container, false)
        val listener = { info: String, src: String ->
            val action = ApdFragmentDirections.actionApdFragmentToPhotoFragment(info, src)
            findNavController().navigate(action)
        }
        val rv = bnd.rv
        ApiService().today(listener, bnd.today, requireContext())
        ApiService().getApd(listener, rv, requireContext())
        bnd.refresh.setOnClickListener {
            GsonJson(requireContext()).sharedPref.edit().clear().apply()
            context?.cacheDir?.path?.let { File(it).deleteRecursively() }
            ApiService().getApd(listener, rv, requireContext())
        }
        return bnd.root
    }
}