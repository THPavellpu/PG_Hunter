package com.example.pghunter.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.pghunter.R
import com.example.pghunter.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load images using Glide
        Glide.with(this)
            .load("https://ik.imagekit.io/tanmoycodes/PgHunter/Aditya.png")
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(binding.imgAditya)

        Glide.with(this)
            .load("https://ik.imagekit.io/tanmoycodes/PgHunter/Tanmoy2.jpg")
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(binding.imgTanmoy)

        Glide.with(this)
            .load("https://ik.imagekit.io/tanmoycodes/PgHunter/Karan.png")
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(binding.imgKaran)

        // Tanvir image from drawable
        binding.imgTanvir.setImageResource(R.drawable.thp)
        binding.imgRanjit.setImageResource(R.drawable.ranjit)



        // Back button click

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
