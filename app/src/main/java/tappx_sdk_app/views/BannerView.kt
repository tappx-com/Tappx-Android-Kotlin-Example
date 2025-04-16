package tappx_sdk_app.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import com.example.tappxsdkapp.R
import tappx_sdk_app.ads.AdSubType
import tappx_sdk_app.ads.AdType
import tappx_sdk_app.ads.BannerAd
import tappx_sdk_app.ads.MRECAd

class BannerView : Activity() {

    private var adFormat: AdType? = null
    private var adContainer: ViewGroup? = null
    private var statusLog: TextView? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.banner_view)

        // Get the ad container view
        adContainer = findViewById<ViewGroup>(R.id.adContainer)

        // Get the status log
        statusLog = findViewById<TextView>(R.id.statusLog)

        // Retrieve the ad format (banner or MREC) from the Intent
        adFormat = AdType.valueOf(getButtonClicked()!!)

        // Set the text based on the ad type
        val adFormatText = findViewById<TextView>(R.id.adFormatText)
        if (adFormat == AdType.Banner) {
            adFormatText.text = AdType.Banner.name
            loadBannerAd()
        } else {
            adFormatText.text = AdType.MREC.name
            loadMRECAd()
        }
    }

    // Method to get the clicked button's value from the Intent
    private fun getButtonClicked(): String? {
        val intent = intent
        return intent.getStringExtra("buttonClicked")
    }

    // Method to load the Banner ad
    private fun loadBannerAd() {
        // Create an instance of BannerAd and load the banner ad
        val bannerAd = BannerAd(this, adContainer!!, statusLog!!)
        bannerAd.adSubType = AdSubType.phone_320X50 // "phone_320X50" is an example of AdSubType you can add "tablet_728x90" or "smart" too
        bannerAd.loadBanner()
    }

    // Method to load the MREC ad
    private fun loadMRECAd() {
        //Create an instance of MRECAd and load the MREC ad
        val mrecAd = MRECAd(this, adContainer!!, statusLog!!)
        mrecChangeDesign()
        mrecAd.loadMRECAd()
    }

    // Method to handle the back button click and navigate to MainView
    fun onClickGoMainView(view: View?) {
        // Navigate back to the main view
        val intent = Intent(this@BannerView, MainView::class.java)
        startActivity(intent)
    }

    private fun mrecChangeDesign() {
        //Apply new margins

        val marginTopInDp = 10
        val marginTopInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            marginTopInDp.toFloat(),
            resources.displayMetrics
        ).toInt()

        //Top Margin
        val params = adContainer!!.layoutParams as MarginLayoutParams
        params.topMargin = marginTopInPx
        adContainer!!.layoutParams = params

        //Bottom Linear layout
        val statusContainer = findViewById<LinearLayout>(R.id.linearLayouBottom)
        val statusParams = statusContainer.layoutParams as MarginLayoutParams
        statusParams.topMargin = marginTopInPx
        statusContainer.layoutParams = statusParams
    }
}