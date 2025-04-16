package tappx_sdk_app.ads

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import com.example.tappxsdkapp.R
import com.tappx.sdk.android.AdRequest
import com.tappx.sdk.android.TappxAdError
import com.tappx.sdk.android.TappxBanner
import com.tappx.sdk.android.TappxBannerListener

class BannerAd(
    private val context: Context,
    private val bannerContainer: ViewGroup,
    private val statusLog: TextView
) {
    private var banner: TappxBanner? = null

    var adSubType: AdSubType? = null
        get() = field
        set(value) {
            field = value
        }

    fun loadBanner() {
        banner = TappxBanner(context, context.getString(R.string.tappx_key))
        when (adSubType) {
            AdSubType.phone_320X50 -> banner!!.setAdSize(TappxBanner.AdSize.BANNER_320x50)
            AdSubType.tablet_728x90 -> banner!!.setAdSize(TappxBanner.AdSize.BANNER_728x90)
            AdSubType.smart -> banner!!.setAdSize(TappxBanner.AdSize.SMART_BANNER)
            else -> banner!!.setAdSize(TappxBanner.AdSize.BANNER_320x50)
        }

        // Add Banner to container
        bannerContainer.addView(banner)

        // Set refresh configuration
        banner!!.setRefreshTimeSeconds(45)
        banner!!.setEnableAutoRefresh(false)

        // LoadAd
        banner!!.loadAd(
            AdRequest()
                .useTestAds(context.resources.getBoolean(R.bool.useTestAds))
                .setEndpoint(context.getString(R.string.endpoint))
        )

        // Add Listeners
        banner!!.setListener(object : TappxBannerListener {
            override fun onBannerLoaded(tappxBanner: TappxBanner) {
                // Update the log when the banner is successfully loaded
                val message = "Banner Loaded Successfully!"
                updateLog(message)
            }

            override fun onBannerLoadFailed(tappxBanner: TappxBanner, tappxAdError: TappxAdError) {
                // Log the error when banner load fails
                val message = "Banner Load Failed"
                updateLog(message)
            }

            override fun onBannerClicked(tappxBanner: TappxBanner) {
                // Log the click event
                val message = "Banner Clicked!"
                updateLog(message)
            }

            override fun onBannerExpanded(tappxBanner: TappxBanner) {
                // Log the banner expansion
                val message = "Banner Expanded!"
                updateLog(message)
            }

            override fun onBannerCollapsed(tappxBanner: TappxBanner) {
                // Log the banner collapse
                val message = "Banner Collapsed!"
                updateLog(message)
            }
        })
    }

    // Method to update the TextView inside the ScrollView with log messages
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