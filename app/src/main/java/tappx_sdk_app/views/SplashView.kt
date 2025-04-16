package tappx_sdk_app.views

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StyleSpan
import android.widget.TextView
import com.example.tappxsdkapp.R
import java.util.Timer
import java.util.TimerTask

class SplashView: Activity() {

    // Duration for the splash screen (in milliseconds)
    private val SPLASH_TIME: Long = 4000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_view)

        // Design part
        val sloganText = findViewById<TextView>(R.id.sloganText)
        val spannableString = SpannableString(this.getString(R.string.slogan))
        spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, 11, 0)
        sloganText.text = spannableString

        startMainActivityWithDelay()
    }

    /**
     * Starts MainView after a specified delay using a TimerTask.
     */
    private fun startMainActivityWithDelay() {
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                // Create an intent to transition from SplashView to MainView
                val intent = Intent(applicationContext, MainView::class.java)
                startActivity(intent)
                finish()
            }
        }

        // Schedule the task to run after SPLASH_TIME milliseconds
        Timer().schedule(timerTask, SPLASH_TIME)
    }
}