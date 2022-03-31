package com.example.basic_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    var nLineNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "${nLineNumber++} onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "${nLineNumber++} onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "${nLineNumber++} onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "${nLineNumber++} onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "${nLineNumber++} onDestroy")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "${nLineNumber++} onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "${nLineNumber++} onPause")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "${nLineNumber++} onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "${nLineNumber++} onRestoreInstanceState")
    }

}