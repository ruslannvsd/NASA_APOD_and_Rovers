package com.example.nasaphotos.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaphotos.adaps.RoverPhotosAdapter
import com.example.nasaphotos.databinding.FragmentRoverPhotosBinding
import com.example.nasaphotos.network.PhotosViewModel
import java.io.File

class RoverPhotosFragment : Fragment() {
    private val args: RoverPhotosFragmentArgs by navArgs()
    private lateinit var bnd: FragmentRoverPhotosBinding
    private lateinit var vm: PhotosViewModel
    private lateinit var ad: RoverPhotosAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                    context?.cacheDir?.path?.let { File(it).deleteRecursively() }
                }
            })
        vm = ViewModelProvider(this)[PhotosViewModel::class.java]
        bnd = FragmentRoverPhotosBinding.inflate(inflater, container, false)
        val infoShare = { info: String, src: String ->
            val action = RoverPhotosFragmentDirections.actionRoverPhotosFragmentToPhotoFragment(info, src)
            findNavController().navigate(action)
        }
        val rv = bnd.rv
        vm.photosViewModel(args.rover, args.sol).observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "${it.size} photos", Toast.LENGTH_LONG).show()
            ad = RoverPhotosAdapter(args.height, infoShare, it)
            rv.adapter = ad
            rv.layoutManager = LinearLayoutManager(requireContext())
            ad.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
        return bnd.root
    }
}