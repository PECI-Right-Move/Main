package com.example.rightmove

import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.gson.Gson

private const val  CAMERA_REQUEST_CODE = 101

private var scanning = true

class assembly : AppCompatActivity() {

    private lateinit var codeScanner : CodeScanner

    private lateinit var tv_textView : TextView

    private lateinit var scanner_view: CodeScannerView

    private lateinit var image_view : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assembly)

        tv_textView = findViewById(R.id.tv_textView)
        scanner_view = findViewById(R.id.scanner_view)
        image_view = findViewById(R.id.iv_assembly_image)

        setupPermissions()
        codeScanner()

        // Set scanning to true when the activity starts
        scanning = true
    }

    private fun codeScanner() {
        codeScanner = CodeScanner(this, scanner_view)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback { result ->
                runOnUiThread {
                    try {
                        val qrData = Gson().fromJson(result.text, AssemblyData::class.java)
                        val pieces = arrayOf(
                            "1st Piece: ${qrData.piece1}",
                            "2nd Piece: ${qrData.piece2}",
                            "3rd Piece: ${qrData.piece3}",
                            "4th Piece: ${qrData.piece4}"
                        )
                        val checkedItems = BooleanArray(pieces.size)

                        val builder = AlertDialog.Builder(this@assembly)
                        builder.setTitle("${qrData.name}")
                        builder.setMultiChoiceItems(pieces, checkedItems) { _, which, isChecked ->
                            // Update the checked items array
                            checkedItems[which] = isChecked
                        }
                        builder.setPositiveButton("OK") { _, _ ->
                            // Close the pop-up window and restart scanning
                            scanning = true
                            codeScanner.startPreview()
                        }
                        builder.setOnCancelListener {
                            // Restart scanning when the user dismisses the pop-up window
                            scanning = true
                            codeScanner.startPreview()
                        }
                        builder.show()

                        scanning = false
                    } catch (e: Exception) {
                        // Show a pop-up window with an error message and a return button
                        AlertDialog.Builder(this@assembly)
                            .setTitle("Invalid QR")
                            .setMessage("The scanned QR code is invalid.")
                            .setPositiveButton("Return") { _, _ ->
                                // Hide the pop-up window and restart scanning
                                scanning = true
                                codeScanner.startPreview()
                            }
                            .show()
                    }
                }
            }

            errorCallback = ErrorCallback { error ->
                runOnUiThread {
                    Log.e("Main", "Camera initialization error: ${error.message}")
                }
            }
        }

        scanner_view.setOnClickListener {
            codeScanner.startPreview()
        }
    }





    override fun onResume() {
        super.onResume()
        if (scanning) {
            codeScanner.startPreview()
        }
    }

    override fun onPause() {
        if (scanning) {
            codeScanner.releaseResources()
        }
        super.onPause()
    }

    override fun onDestroy() {
        codeScanner.releaseResources()
        super.onDestroy()
    }



    @Deprecated("Use the new method finishScan instead")
    override fun onBackPressed() {
        if (!scanning) {
            tv_textView.visibility = View.GONE
            image_view.visibility = View.GONE

            scanning = true
            codeScanner.startPreview()
        } else {
            super.onBackPressed()
        }
    }

    private fun setupPermissions(){
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0]!= PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "you need camera permission to use Right Move", Toast.LENGTH_SHORT).show()
                }else{
                    // Permissao garantida

                }
            }
        }
    }
}