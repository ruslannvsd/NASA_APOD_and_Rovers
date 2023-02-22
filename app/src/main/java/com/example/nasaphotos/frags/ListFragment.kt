package com.example.nasaphotos.frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaphotos.adaps.ListAdapter
import com.example.nasaphotos.databinding.FragmentListBinding
import com.example.nasaphotos.network.PhotosViewModel
import java.io.File

class ListFragment : Fragment() {
    private val args: ListFragmentArgs by navArgs()
    private lateinit var bnd: FragmentListBinding
    private lateinit var vm: PhotosViewModel
    private lateinit var ad: ListAdapter
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
        bnd = FragmentListBinding.inflate(inflater, container, false)
        val listener = { info: String, src: String ->
            val action = ListFragmentDirections.actionListFragmentToPhotoFragment(info, src)
            findNavController().navigate(action)
        }
        val rv = bnd.rv
        vm.photosViewModel(args.rover, args.sol).observe(viewLifecycleOwner) {
            ad = ListAdapter(args.height, listener, it)
            rv.adapter = ad
            rv.layoutManager = LinearLayoutManager(requireContext())
            ad.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
        return bnd.root
    }
}