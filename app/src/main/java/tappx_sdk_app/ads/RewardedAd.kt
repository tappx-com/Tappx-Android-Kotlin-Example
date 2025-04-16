package tappx_sdk_app.ads

import android.content.Context
import android.util.Log
import android.widget.TextView
import com.example.tappxsdkapp.R
import com.tappx.sdk.android.AdRequest
import com.tappx.sdk.android.TappxAdError
import com.tappx.sdk.android.TappxRewardedVideo
import com.tappx.sdk.android.TappxRewardedVideoListener

class RewardedAd(
    private val context: Context,
    private val statusLog: TextView
) {
    private var rewardedVideo: TappxRewardedVideo? = null

    /*
    * REWARDED AD
    */
    fun loadRewardedAd() {
        rewardedVideo = TappxRewardedVideo(context, context.getString(R.string.tappx_key))
        rewardedVideo!!.loadAd(
            AdRequest()
                .useTestAds(context.resources.getBoolean(R.bool.useTestAds))
                .setEndpoint(context.getString(R.string.endpoint))
        )

        // Add Listeners
        rewardedVideo!!.setListener(object : TappxRewardedVideoListener {
            override fun onRewardedVideoLoaded(tappxRewardedVideo: TappxRewardedVideo) {
                // Log when the rewarded video is successfully loaded
                val message = "Rewarded Video Loaded Successfully!"
                updateLog(message)
            }

            override fun onRewardedVideoLoadFailed(
                tappxRewardedVideo: TappxRewardedVideo,
                tappxAdError: TappxAdError
            ) {
                // Log when the rewarded video fails to load
                val message = "Rewarded Video Load Failed!"
                updateLog(message)
            }

            override fun onRewardedVideoStart(tappxRewardedVideo: TappxRewardedVideo) {
                // Log when the rewarded video starts playing
                val message = "Rewarded Video Started!"
                updateLog(message)
            }

            override fun onRewardedVideoPlaybackFailed(tappxRewardedVideo: TappxRewardedVideo) {
                // Log when the rewarded video playback fails
                val message = "Rewarded Video Playback Failed!"
                updateLog(message)
            }

            override fun onRewardedVideoClicked(tappxRewardedVideo: TappxRewardedVideo) {
                // Log when the rewarded video is clicked
                val message = "Rewarded Video Clicked!"
                updateLog(message)
            }

            override fun onRewardedVideoClosed(tappxRewardedVideo: TappxRewardedVideo) {
                // Log when the rewarded video is closed
                val message = "Rewarded Video Closed!"
                updateLog(message)
            }

            override fun onRewardedVideoCompleted(tappxRewardedVideo: TappxRewardedVideo) {
                // Log when the rewarded video is completed
                val message = "Rewarded Video Completed!"
                updateLog(message)
            }
        })
    }

    fun showRewardedAd() {
        if (rewardedVideo != null && rewardedVideo!!.isReady) {
            rewardedVideo!!.show()
        } else {
            updateLog("Rewarded Ad is not loaded")
        }
    }

    fun showRewardedStatus() {
        if (rewardedVideo == null) {
            updateLog("Rewarded Ad is not loaded")
            return
        }
        if (rewardedVideo!!.isReady) {
            updateLog("Rewarded Ad is ready to show!")
        } else {
            updateLog("Rewarded Ad is not ready yet")
        }
    }

    private fun updateLog(message: String) {
        if (statusLog.text.isNotEmpty()) {
            statusLog.append("\n$message")
        } else {
            statusLog.append(message)
        }
        Log.e("Tappx SDK", message)
    }

}