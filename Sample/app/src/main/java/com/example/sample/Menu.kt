package com.example.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }

    fun openScanActivity(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun openAssemblyActivity(view: View) {
        val intent = Intent(this, assembly::class.java)
        startActivity(intent)
    }

    fun openProjection(view: View) {
        val intent = Intent(this, colorVerification::class.java)
        startActivity(intent)
    }

}