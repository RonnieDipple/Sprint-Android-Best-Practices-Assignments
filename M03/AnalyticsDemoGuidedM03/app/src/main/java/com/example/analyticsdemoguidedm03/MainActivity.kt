package com.example.analyticsdemoguidedm03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //1st
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, "MainActivity", "test2")

        textView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1")
            bundle.putString(FirebaseAnalytics.Param.QUANTITY, "42")
            FirebaseAnalytics.getInstance(this).logEvent("main_screen_view", bundle)

            Toast.makeText(this, "text_view_clicked", Toast.LENGTH_SHORT).show()
        }

    }
}
