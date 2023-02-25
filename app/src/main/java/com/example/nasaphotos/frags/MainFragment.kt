package com.example.nasaphotos.frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.nasaphotos.R
import com.example.nasaphotos.databinding.FragmentMainBinding
import com.example.nasaphotos.funs.Cons
import com.example.nasaphotos.funs.Funs
import com.example.nasaphotos.funs.GsonJson
import java.io.File

class MainFragment : Fragment() {
    private lateinit var bnd: FragmentMainBinding
    private lateinit var sol: EditText
    private lateinit var hei: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        context?.cacheDir?.path?.let { File(it).deleteRecursively() }
        bnd = FragmentMainBinding.inflate(inflater, container, false)
        sol = bnd.sol // sol is a full Martian day
        hei = bnd.height // height of the image to remove unnecessary and small images from the list
        if (!Funs.wiFi(requireContext())) { // since one list of images may be of 1GB size I restricted to open it if there's no WiFi
            Toast.makeText(requireContext(), "Only APOD works", Toast.LENGTH_LONG).show() // but Apod works, there are only 10 images to download
        }

        val solCTotal = sol(1344226677000) // starting millis of Curiosity exploration on Mars
        val solPTotal = sol(1613681700000) // starting millis of Perseverance  exploration on Mars

        (Cons.CURIOSITY + " " + solCTotal).also { bnd.curiosity.text = it }
        (Cons.PERSEVERANCE + " " + solPTotal).also { bnd.perseverance.text = it }
        (Cons.OPPORTUNITY + " " + Cons.SOLS_OPP.toString()).also { bnd.opportunity.text = it }
        (Cons.SPIRIT + " " + Cons.SOLS_SPI.toString()).also { bnd.spirit.text = it }

        bnd.cur.setOnClickListener {
            rover(solCTotal, Cons.CURIOSITY)
        }
        bnd.per.setOnClickListener {
            rover(solPTotal, Cons.PERSEVERANCE)
        }
        bnd.opp.setOnClickListener {
            rover(Cons.SOLS_OPP, Cons.OPPORTUNITY)
        }
        bnd.spi.setOnClickListener {
            rover(Cons.SOLS_SPI, Cons.SPIRIT)
        }
        bnd.apod.setOnClickListener {// preventive clearing of sharedPrefs
            val editor = GsonJson(requireContext()).sharedPref.edit()
            editor.putString(Cons.SAL, null)
            editor.apply()
            findNavController().navigate(R.id.action_mainFragment_to_apdFragment)
        }
        return bnd.root
    }
    private fun sol(start: Long) : Int { // getting today's sol on Mars (better say tosol)
        val now = System.currentTimeMillis()
        val diff = now - start
        return (diff / 88775000).toInt()
    }

    private fun rover(totalSol: Int, roverName: String) {
        if (Funs.wiFi(requireContext())) {
            val solChosen = sol.text
            val hei = height(hei)

            if (solChosen.isNotEmpty()) {
                val sol = solChosen.toString()
                if (sol.toInt() in 1..totalSol) {
                    navigate(roverName, sol, hei)
                } else Toast.makeText(requireContext(), "Enter an existing sol", Toast.LENGTH_LONG)
                    .show()
            } else if (roverName == Cons.CURIOSITY || roverName == Cons.PERSEVERANCE) {
                val ago = totalSol - 2
                Toast.makeText(
                    requireContext(),
                    // Curiosity and Perseverance are still active and update of new photos sometimes takes two sols
                    "Two sols ago $ago", Toast.LENGTH_LONG
                ).show()
                navigate(roverName, ago.toString(), hei)
            } else {
                val random = (0..totalSol).random()
                Toast.makeText(
                    // Opportunity and Spirit have fixed amount of sols so it's good to show random
                    requireContext(), "Random sol $random", Toast.LENGTH_LONG
                ).show()
                navigate(roverName, random.toString(), hei)
            }
        } else Toast.makeText(requireContext(), "No WiFi", Toast.LENGTH_LONG).show()
    }
    private fun navigate (rover: String, sol: String, height: Int) {
        val action = MainFragmentDirections
            .actionMainFragmentToRoverPhotosFragment(
                rover,
                sol,
                height
            )
        findNavController()
            .navigate(
                action
            )
    }
    private fun height(hei: EditText) : Int {
        var height = 0
        if (hei.text.isEmpty()) height = 200 else
            if (hei.text.toString().toInt() > 900) height = 900 else
                if (hei.text.toString().toInt() < 0) height = 100
        return height
    }
}