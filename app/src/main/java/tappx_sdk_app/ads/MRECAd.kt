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

class MRECAd(
    private val context: Context,
    private val mrecContainer: ViewGroup,
    private val statusLog: TextView
) {
    private var mrec: TappxBanner? = null

    fun loadMRECAd() {
        mrec = TappxBanner(context, context.getString(R.string.tappx_key))
        mrec!!.setAdSize(TappxBanner.AdSize.BANNER_300x250)

        // Add Banner to container
        mrecContainer.addView(mrec)

        // Set refresh configuration
        mrec!!.setRefreshTimeSeconds(45)
        mrec!!.setEnableAutoRefresh(false)

        // LoadAd
        mrec!!.loadAd(
            AdRequest()
                .useTestAds(context.resources.getBoolean(R.bool.useTestAds))
                .setEndpoint(context.getString(R.string.endpoint))
        )

        // Add Listeners
        mrec!!.setListener(object : TappxBannerListener {
            override fun onBannerLoaded(tappxBanner: TappxBanner) {
                // Update the log when the banner is successfully loaded
                val message = "MREC Loaded Successfully!"
                updateLog(message)
            }

            override fun onBannerLoadFailed(tappxBanner: TappxBanner, tappxAdError: TappxAdError) {
                // Log the error when banner load fails
                val message = "MREC Load Failed"
                updateLog(message)
            }

            override fun onBannerClicked(tappxBanner: TappxBanner) {
                // Log the click event
                val message = "MREC Clicked!"
                updateLog(message)
            }

            override fun onBannerExpanded(tappxBanner: TappxBanner) {
                // Log the banner expansion
                val message = "MREC Expanded!"
                updateLog(message)
            }

            override fun onBannerCollapsed(tappxBanner: TappxBanner) {
                // Log the banner collapse
                val message = "MREC Collapsed!"
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