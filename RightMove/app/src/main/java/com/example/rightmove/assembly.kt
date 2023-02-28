package com.example.rightmove

import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import java.io.IOException
import com.google.gson.reflect.TypeToken
import android.os.Looper
import android.util.Log
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.*
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
import org.json.JSONObject
import java.sql.Time
import java.util.concurrent.TimeUnit

private const val  CAMERA_REQUEST_CODE = 101

private var scanning = true

class assembly : AppCompatActivity() {

    private lateinit var codeScanner : CodeScanner

    private lateinit var tv_textView : TextView

    private lateinit var scanner_view: CodeScannerView

    private lateinit var image_view : ImageView

    private lateinit var qrInfoLayout: RelativeLayout

    private lateinit var qrInfoTitle: TextView

    private lateinit var qrInfoDetails: TextView






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_assembly)

        tv_textView = findViewById(R.id.tv_textView)
        scanner_view = findViewById(R.id.scanner_view)
        image_view = findViewById(R.id.iv_assembly_image)




        qrInfoLayout = findViewById(R.id.qr_info_layout)
        qrInfoTitle = findViewById(R.id.qr_info_title)
        qrInfoDetails = findViewById(R.id.qr_info_details)

        setupPermissions()
        firstcodeScanner()

        // Set scanning to true when the activity starts
        scanning = true
    }

    private fun firstcodeScanner() {
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

                        val piecesIds = arrayOf( "${qrData.piece1}",
                            "${qrData.piece2}",
                            "${qrData.piece3}",
                            "${qrData.piece4}")


                        var currentPiece = 0
                        val collected = mutableListOf<String>()

                        // Update the QR code information views
                        qrInfoTitle.text = qrData.name
                        qrInfoDetails.text = pieces.joinToString(separator = "\n")

                        // Show the QR code information layout
                        qrInfoLayout.visibility = View.VISIBLE


                        tv_textView.text = qrData.name.toString()
                        val resourceId = resources.getIdentifier(qrData.name, "drawable", packageName)
                        image_view.setImageResource(resourceId)
                        image_view.visibility = View.VISIBLE //
                        tv_textView.visibility = View.GONE

                        Handler(Looper.getMainLooper()).postDelayed({
                        codeScanner.decodeCallback = DecodeCallback { result ->

                            runOnUiThread {

                                try {
                                        val qrData = Gson().fromJson(result.text, QRCodeData::class.java)
                                        val secondQRId = qrData.id


                                           if (secondQRId == piecesIds[currentPiece]) {

                                               // show "Piece 1 Collected" notification
                                               Toast.makeText(this@assembly, "${secondQRId} Collected", Toast.LENGTH_SHORT).show()
                                               collected.add(secondQRId)
                                               currentPiece++

                                               //read json////////////////////
                                               val ler = read_json(applicationContext, "instructions.json")
                                               Log.i("data", ler.toString())
                                               val gson = Gson()
                                               val listPersonType = object : TypeToken<List<Instructions>>() {}.type

                                               var list: List<Instructions> = gson.fromJson(ler, listPersonType)
                                               list.forEachIndexed { idx, list -> Log.i("data", "> Item $idx:\n$list") }
                                               val step = resources.getIdentifier(list.toString(), "drawable", packageName)
                                               image_view.setImageResource(step)
                                               //miss add display image


                                               if(currentPiece==4){

                                                   // Show a pop-up window with an error message and a return button
                                                   AlertDialog.Builder(this@assembly)
                                                       .setTitle("Your Pieces were all collected!")
                                                       .setMessage("Now you can start your assembly")
                                                       .setPositiveButton("Collect pieces for a new Assembly") { _, _ ->
                                                           scanning = true
                                                           currentPiece = 0
                                                           collected.clear()
                                                           codeScanner.startPreview()
                                                           firstcodeScanner()

                                                       }
                                                       .show()

                                               }

                                           } else {
                                               // show "Wrong Piece" notification
                                               Toast.makeText(this@assembly, "Wrong Piece, you collected ${secondQRId} ", Toast.LENGTH_SHORT).show()
                                           }



                                        // update views, etc.
                                        val resourceId = resources.getIdentifier("step1", "drawable", packageName)
                                        image_view.setImageResource(resourceId)


                                        scanning = false
                                        image_view.visibility = View.VISIBLE
                                        tv_textView.visibility = View.GONE




                                    // Start the preview of the first scanner
                                    codeScanner.startPreview()
                                } catch (e: Exception) {
                                    // Show a pop-up window with an error message and a return button
                                    AlertDialog.Builder(this@assembly)
                                        .setTitle("Invalid QR")
                                        .setMessage("The scanned QR code is invalid.")
                                        .setPositiveButton("Return") { _, _ ->
                                            scanning = true
                                            codeScanner.startPreview()
                                        }
                                        .show()
                                }
                            }
                        }}, 5000)

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

    fun read_json (context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open("instructions.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
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