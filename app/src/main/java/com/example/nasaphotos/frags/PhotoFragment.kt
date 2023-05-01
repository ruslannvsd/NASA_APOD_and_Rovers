package com.example.nasaphotos.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nasaphotos.data.ResizeTransformation
import com.example.nasaphotos.databinding.FragmentPhotoBinding
import com.example.nasaphotos.funs.CopyShare
import com.squareup.picasso.Picasso

class PhotoFragment : Fragment() {
    private val args: PhotoFragmentArgs by navArgs()
    private lateinit var bnd: FragmentPhotoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack() // clearing cache
                }
            })
        val src = args.src
        val info = args.info + "\n" + src // collecting information to share
        bnd = FragmentPhotoBinding.inflate(inflater, container, false)
        val pic = bnd.pic

        Picasso.get()
            .load(src)
            .transform(ResizeTransformation(5000))
            .into(pic)

        pic.setOnClickListener {
            CopyShare.copyShare(requireContext(), info, requireActivity()) // opening small menu
        }
        return bnd.root
    }
}