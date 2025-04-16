package tappx_sdk_app.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.tappxsdkapp.R
import com.tappx.sdk.android.Tappx
import tappx_sdk_app.ads.AdType

class MainView : Activity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_view)

        // SDK Version
        val sdkVersionText = findViewById<TextView>(R.id.sdk_version)
        sdkVersionText.text = getString(R.string.tappx_sdk_version) + Tappx.getVersion()
    }


    // Banner onClick
    fun onBannerButtonClick(view: View?) {
        val intent = Intent(this@MainView, BannerView::class.java)
        intent.putExtra("buttonClicked", AdType.Banner.name)
        startActivity(intent)
    }

    // MREC onClick
    fun onMrecButtonClick(view: View?) {
        val intent = Intent(this@MainView, BannerView::class.java)
        intent.putExtra("buttonClicked", AdType.MREC.name)
        startActivity(intent)
    }

    // Interstitial onClick
    fun onInterstitialButtonClick(view: View?) {
        val intent = Intent(this@MainView, FullScreenView::class.java)
        intent.putExtra("buttonClicked", AdType.Interstitial.name)
        startActivity(intent)
    }

    // Rewarded onClick
    fun onRewardedButtonClick(view: View?) {
        val intent = Intent(this@MainView, FullScreenView::class.java)
        intent.putExtra("buttonClicked", AdType.Rewarded.name)
        startActivity(intent)
    }
}