package tappx_sdk_app.ads

import android.content.Context
import android.util.Log
import android.widget.TextView
import com.example.tappxsdkapp.R
import com.tappx.sdk.android.AdRequest
import com.tappx.sdk.android.TappxAdError
import com.tappx.sdk.android.TappxInterstitial
import com.tappx.sdk.android.TappxInterstitialListener

class InterstitialAd(
    private val context: Context,
    private val statusLog: TextView,
    private var autoShow: Boolean
) {
    private var interstitialAd: TappxInterstitial? = null

    fun setAutoShow(autoShowParam: Boolean) {
        autoShow = autoShowParam
    }

    fun loadInterstitialAd() {
        interstitialAd = TappxInterstitial(context, context.getString(R.string.tappx_key))
        interstitialAd?.setAutoShowWhenReady(autoShow)

        // Add Listeners
        interstitialAd?.setListener(object : TappxInterstitialListener {
            override fun onInterstitialLoaded(tappxInterstitial: TappxInterstitial) {
                val message = "Interstitial Ad Loaded Successfully!"
                updateLog(message)
            }

            override fun onInterstitialLoadFailed(
                tappxInterstitial: TappxInterstitial,
                tappxAdError: TappxAdError
            ) {
                val message = "Interstitial Ad Load Failed!"
                updateLog(message)
            }

            override fun onInterstitialShown(tappxInterstitial: TappxInterstitial) {
                val message = "Interstitial Ad Shown!"
                updateLog(message)
            }

            override fun onInterstitialClicked(tappxInterstitial: TappxInterstitial) {
                val message = "Interstitial Ad Clicked!"
                updateLog(message)
            }

            override fun onInterstitialDismissed(tappxInterstitial: TappxInterstitial) {
                val message = "Interstitial Ad Dismissed!"
                updateLog(message)
            }
        })
        interstitialAd?.loadAd(
            AdRequest()
                .useTestAds(context.resources.getBoolean(R.bool.useTestAds))
                .setEndpoint(context.getString(R.string.endpoint))
        )
    }

    fun showInterstitialAd() {
        if (interstitialAd != null && interstitialAd?.isReady == true) {
            interstitialAd?.show()
        } else {
            updateLog("Interstitial Ad is not loaded")
        }
    }


    fun showInterstitialStatus() {
        if (interstitialAd == null) {
            updateLog("Interstitial Ad is not loaded")
            return
        }
        if (interstitialAd!!.isReady) {
            updateLog("Interstitial Ad is ready to show")
        } else {
            updateLog("Interstitial Ad is not ready yet")
        }
    }


    private fun updateLog(message: String) {
        val currentText = statusLog.text.toString()
        val newText = if (currentText.isNotEmpty()) {
            "$message\n$currentText"
        } else {
            message
        }
        statusLog.text = newText
        Log.e("Tappx SDK", message)
    }
}