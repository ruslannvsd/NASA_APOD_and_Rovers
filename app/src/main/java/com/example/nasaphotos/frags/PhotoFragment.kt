package com.example.nasaphotos.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
                    findNavController().popBackStack()
                }
            })
        val src = args.src
        val info = args.info + "\n" + src
        bnd = FragmentPhotoBinding.inflate(inflater, container, false)
        val pic = bnd.pic
        Picasso.get().load(src).into(pic)
        pic.setOnLongClickListener {
            CopyShare.copyShare(requireContext(), info, requireActivity())
            true
        }
        return bnd.root
    }
}