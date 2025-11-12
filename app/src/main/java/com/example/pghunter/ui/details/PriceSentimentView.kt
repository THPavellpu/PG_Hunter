package com.example.pghunter.ui.details

import android.animation.ObjectAnimator
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.example.pghunter.R

class PriceSentimentView(private val rootView: View) {

    private val barContainer: FrameLayout = rootView.findViewById(R.id.barContainer)
    private val indicator: View = rootView.findViewById(R.id.indicator)
    private val descriptionText: TextView = rootView.findViewById(R.id.tvPriceSentimentDescription)

    fun setPriceSentiment(score: Float, description: String) {
        // Clamp score between 0.0 and 1.0
        val clampedScore = score.coerceIn(0f, 1f)

        // Wait until layout is measured to calculate movement range
        barContainer.post {
            val maxTranslation = barContainer.width - indicator.width

            // Calculate new X position based on score
            val newPosition = clampedScore * maxTranslation

            // Animate the indicator smoothly
            ObjectAnimator.ofFloat(indicator, "translationX", newPosition).apply {
                duration = 800
                start()
            }
        }

        // Set description
        descriptionText.text = description
    }
}
