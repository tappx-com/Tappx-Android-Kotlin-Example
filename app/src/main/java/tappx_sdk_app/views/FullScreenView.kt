package tappx_sdk_app.views

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.tappxsdkapp.R
import tappx_sdk_app.ads.AdType
import tappx_sdk_app.ads.InterstitialAd
import tappx_sdk_app.ads.RewardedAd

class FullScreenView : Activity() {
    private var adFormat: AdType? = null
    private lateinit var statusLog: TextView
    private var rewardedAd: RewardedAd? = null
    private var interstitialAd: InterstitialAd? = null
    private lateinit var interstitialSwitch: SwitchCompat
    private lateinit var autoShowText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_screen_view)

        // Initialize views
        statusLog = findViewById(R.id.statusLog)
        interstitialSwitch = findViewById(R.id.interstitialSwitch)
        autoShowText = findViewById(R.id.autoShowText)

        // Retrieve the ad format (Rewarded or Interstitial) from the Intent
        adFormat = AdType.valueOf(adFormatFromIntent ?: "")
        // Set up the UI based on the ad format
        setupAdUI()

        // Initialize Interstitial Ad if needed
        if (adFormat == AdType.Interstitial) {
            interstitialAd = InterstitialAd(this, statusLog, interstitialSwitch.isChecked)
        }
    }

    private val adFormatFromIntent: String?
        get() = intent.getStringExtra("buttonClicked")

    private fun setupAdUI() {
        val adFormatText: TextView = findViewById(R.id.adFormatText)
        if (adFormat == AdType.Rewarded) {
            interstitialSwitch.visibility = View.GONE
            autoShowText.visibility = View.GONE
            adFormatText.text = AdType.Rewarded.name
            rewardedAd = RewardedAd(this, statusLog)
        } else {
            adFormatText.text = AdType.Interstitial.name
            configureAutoShowButton()
        }
    }

    fun onLoadFullScreenAdClick(view: View?) {
        when (adFormat) {
            AdType.Rewarded -> rewardedAd?.loadRewardedAd()
            AdType.Interstitial -> {
                interstitialAd = InterstitialAd(this, statusLog, interstitialSwitch.isChecked)
                interstitialAd?.setAutoShow(interstitialSwitch.isChecked)
                interstitialAd?.loadInterstitialAd()
            }
            else -> {
                Log.e("Tappx SDK", "FullScreenView :: Unexpected ad format: $adFormat")
            }
        }
    }


    fun onCheckStatusFullScreenAdClick(view: View?) {
        when (adFormat) {
            AdType.Rewarded -> rewardedAd?.showRewardedStatus()
            AdType.Interstitial -> interstitialAd?.showInterstitialStatus()
            else -> {
                Log.e("Tappx SDK", "FullScreenView :: Unexpected ad format: $adFormat")
            }
        }
    }

    fun onShowFullScreenAdClick(view: View?) {
        when (adFormat) {
            AdType.Rewarded -> rewardedAd?.showRewardedAd()
            AdType.Interstitial -> interstitialAd?.showInterstitialAd()
            else -> {
                Log.e("Tappx SDK", "FullScreenView :: Unexpected ad format: $adFormat")
            }
        }
    }

    private fun configureAutoShowButton() {
        interstitialSwitch.setOnCheckedChangeListener { _, isChecked ->
            interstitialSwitch.thumbTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            val color = if (isChecked) ContextCompat.getColor(this, R.color.green) else ContextCompat.getColor(this, R.color.gray)
            interstitialSwitch.trackTintList = ColorStateList.valueOf(color)

            val checkStatusButton: LinearLayout = findViewById(R.id.checkStatusButton)
            val showAdButton: LinearLayout = findViewById(R.id.showAdButton)
            val linearLayoutBottom: LinearLayout = findViewById(R.id.linearLayouBottom)
            val constraintParams = linearLayoutBottom.layoutParams as ConstraintLayout.LayoutParams

            if (isChecked) {
                checkStatusButton.visibility = View.GONE
                showAdButton.visibility = View.GONE
                constraintParams.topMargin = convertDpToPx(150)
            } else {
                checkStatusButton.visibility = View.VISIBLE
                showAdButton.visibility = View.VISIBLE
                constraintParams.topMargin = convertDpToPx(280)
            }

            linearLayoutBottom.layoutParams = constraintParams

            if (interstitialAd == null) {
                interstitialAd = InterstitialAd(this, statusLog, isChecked)
            }
        }
    }


    private fun convertDpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }

    fun onClickGoMainView(view: View?) {
        val intent = Intent(this, MainView::class.java)
        startActivity(intent)
    }
}
