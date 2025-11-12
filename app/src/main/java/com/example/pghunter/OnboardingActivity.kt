package com.example.pghunter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.pghunter.databinding.ActivityOnboardingBinding
import com.example.pghunter.ui.adapters.OnboardingAdapter

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var adapter: OnboardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val images = listOf(
            R.drawable.onboard1,
            R.drawable.onboard2,
            R.drawable.onboard3
        )

        adapter = OnboardingAdapter(images)
        binding.viewPager.adapter = adapter

        // ✅ When user reaches last page and clicks button
        binding.btnGetStarted.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // ✅ Show button only on last page
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.btnGetStarted.visibility =
                    if (position == images.size - 1) ViewPager2.VISIBLE else ViewPager2.INVISIBLE
            }
        })
    }
}
