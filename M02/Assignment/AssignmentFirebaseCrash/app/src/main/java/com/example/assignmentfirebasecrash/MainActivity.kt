package com.example.assignmentfirebasecrash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NullPointerException

/*Module 2: Android Crash Reporting

## Build a Crashing App and View Reporting

## Instructions:

1. Create an app and add Crashlytics
2. Create an Activity that includes 4 buttons
3. Each button will generate a different kind of crash
4. Add breadcrumbs to each button
5. Inspect the Crashlytics reports to see the crashes (issues)

## Stretch goals
1. Turn on ProGuard in your app
2. Use (this doc)[https://firebase.google.com/docs/crashlytics/get-deobfuscated-reports?platform=android] to deobfuscate your crashes
3. See that the crash logs in your Logcat are obfuscated, but the Crashlytics logs are deobfuscated*/
class MainActivity : AppCompatActivity() {

    companion object{
        val arrayWords = arrayOf("This", "Will", "Crash", "After", "6", "Clicks")
    }
    private var index = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button_On_Click.setOnClickListener {
            dropBreadCrumb("MainActivity", "ThrowableCrashListener", 0L, "Button Click Throwable Force Crash")
            throw RuntimeException("This is a forced crash")

        }

        button_divide_by_zero.setOnClickListener {
            dropBreadCrumb("MainActivity", "Divide by zero Crash", 2L, "Button Divide by 0 Crash")

            val n = 0
            val y = 0
            n/y


        }

        button_null_pointer.setOnClickListener {
            dropBreadCrumb("MainActivity", "Null pointer Crash", 2L, "Button null pointer Crash")

            throw NullPointerException()


        }

        button_out_of_bounds_exception.setOnClickListener {
            dropBreadCrumb("MainActivity", "Next Word Crash", 3L, "Button Out Of Bounds Click Crash")
            textView.text = arrayWords[index]
            index++


        }



    }
}
