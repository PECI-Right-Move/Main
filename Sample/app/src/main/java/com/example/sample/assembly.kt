package com.example.sample

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.sql.Time
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule
import kotlin.coroutines.Continuation
import kotlin.system.exitProcess

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

    private lateinit var jsonInfo : TextView

    private val instructionsList = mutableListOf<Instruction>()

    private val piecesList = mutableListOf<QRCodeData>()

    private lateinit var codeAssembly: String

    private var color : String = ""

    var currentPiece = -1

    private lateinit var piecesIds: Array<String>






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_assembly)

        tv_textView = findViewById(R.id.tv_textView)
        scanner_view = findViewById(R.id.scanner_view)
        image_view = findViewById(R.id.iv_assembly_image)


        qrInfoLayout = findViewById(R.id.qr_info_layout)
        qrInfoTitle = findViewById(R.id.qr_info_title)
        qrInfoDetails = findViewById(R.id.qr_info_details)

        jsonInfo = findViewById(R.id.jsonInfo)

        codeScanner = CodeScanner(this, scanner_view)

        setupPermissions()
        readJson()
        Log.i("MyAPP", "antes da f")
        firstcodeScanner()
        Log.i("MyAPP", "antes epois da f")


        // Set scanning to true when the activity starts
        scanning = true
    }

    private fun readJson(){

        var json: String? = null
        var jsonPieces: String? = null
        try{
            val input: InputStream = assets.open("instructions.json")
            json = input.bufferedReader().use{it.readText()}
            instructionsList.addAll(Gson().fromJson(json, Array<Instruction>::class.java).toList())
            val inputPieces: InputStream = assets.open("pieces.json")
            jsonPieces = inputPieces.bufferedReader().use{it.readText()}
            piecesList.addAll(Gson().fromJson(jsonPieces, Array<QRCodeData>::class.java).toList())


        } catch (e : IOException)
        {

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
            //codeScanner.startPreview()
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

    //Reads the QRCode of Assemblys
    private fun firstcodeScanner() {

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false


            decodeCallback = DecodeCallback { result ->
                runOnUiThread {
                    try {

                        val qrData = Gson().fromJson(result.text, AssemblyData::class.java)
                        codeAssembly = qrData.assemblyId
                        piecesIds = arrayOf( "${qrData.piece1}",
                            "${qrData.piece2}",
                            "${qrData.piece3}",
                            "${qrData.piece4}")
                        // Read the instructions from the AssemblyData object
                        val instructions = qrData.instructions
                        instructionsList.add(instructions)
                     

                        val matchingAssembly = instructionsList.find { it.assembly == codeAssembly }

                        val stepsWithCoords = matchingAssembly?.steps?.map { step ->
                            "(${step.coordinatesA.x}, ${step.coordinatesA.y})"
                        } ?: emptyList()

                        val piecesWithCoords = piecesIds.mapIndexed { index, pieceId ->
                            val pieceName = piecesList.find { it.id == pieceId }?.name ?: "Unknown"
                            Pair(pieceName, stepsWithCoords.getOrNull(index))
                        }

                        val pieces: String = piecesWithCoords.withIndex().joinToString("\n\n") { (index, piece) ->
                            "Piece ${index + 1} : ${piece.first}\nCoords: ${piece.second}"
                        }


                        // Update the QR code information views
                        qrInfoTitle.text = qrData.name
                        qrInfoDetails.text = pieces

                        // Show the QR code information layout
                        qrInfoLayout.visibility = View.VISIBLE


                        tv_textView.text = qrData.name.toString()
                        val resourceId = resources.getIdentifier(qrData.name, "drawable", packageName)
                        image_view.setImageResource(resourceId)
                        image_view.visibility = View.VISIBLE
                        tv_textView.visibility = View.GONE

                        currentPiece = -1

                        secondcodeScanner()
                        scanning = false
                    } catch (e: Exception) {
                        // Show a pop-up window with an error message and a return button
                        AlertDialog.Builder(this@assembly)
                            .setTitle("Invalid QR")
                            .setMessage("The scanned QR code is invalid. asdasdasdsa")
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
    }

    //Reads QRcodes of pieces
    private fun secondcodeScanner() {
        Log.e("MYAPP", "Entrou Second")

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false

            codeScanner.startPreview()

            //Waits so We have time to move camera from the 1st QR to the next
            Timer().schedule(500){

            codeScanner.decodeCallback = DecodeCallback { result ->
                runOnUiThread {
                    try {
                        val qrData =
                            Gson().fromJson(result.text, QRCodeData::class.java)
                        val secondQRId = qrData.id

                        if (secondQRId == piecesIds[currentPiece + 1]) {
                            // show "Piece 1 Collected" notification
                            Toast.makeText(
                                this@assembly,
                                "${secondQRId} Collected",
                                Toast.LENGTH_SHORT
                            ).show()
                            currentPiece++

                            verificationFirst(
                                secondQRId,
                                currentPiece,
                                codeAssembly
                            )

                            Log.d("MYAPP", "Left with " + color)

                            if (currentPiece == 3) {

                                // Show a pop-up window with an error message and a return button
                                AlertDialog.Builder(this@assembly)
                                    .setTitle("Your Pieces were all collected!")
                                    .setMessage("Now you can start your assembly")
                                    .setPositiveButton("Collect pieces for a new Assembly") { _, _ ->
                                        scanning = true
                                        currentPiece = -1
                                        codeScanner.startPreview()
                                        firstcodeScanner()
                                    }
                                    .show()

                            }

                        } else {
                            // show "Wrong Piece" notification
                            Toast.makeText(
                                this@assembly,
                                "Wrong Piece, you collected ${secondQRId} ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        //AQUI
                        // update views, etc.
                        tv_textView.text = qrData.id.toString()
                        val resourceId = resources.getIdentifier(qrData.name, "drawable", packageName)
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
            }
            }
            errorCallback = ErrorCallback { error ->
                runOnUiThread {
                    Log.e("Main", "Camera initialization error: ${error.message}")
                }
            }
        }

        /*scanner_view.setOnClickListener {
            codeScanner.startPreview()
        }*/
    }
    private fun verificationFirst(id: String, index: Int, assembly: String) {
        val instruction = instructionsList.find { it.assembly == assembly }
        if (instruction != null) {
            Toast.makeText(this@assembly, "Verificated ${instruction.assembly}", Toast.LENGTH_SHORT).show()

            switchActivity(instruction.steps[index].coordinatesA.x, instruction.steps[index].coordinatesA.y,instruction.steps[index].coordinatesB.x,instruction.steps[index].coordinatesB.y, instruction.steps[index].color,

                object : ColorSelectedListener {
                    override fun onColorSelected(color: String) {
                        //Enters if color is valid if not decrement from currentPiece to repeat process
                        if (instruction.assembly == assembly && instruction.steps[index].idPiece == id && instruction.steps[index].color.uppercase() == color.uppercase()) {
                            Log.e("MYAPP", color)
                            val resourceId = resources.getIdentifier(instruction.steps[index].idStep, "drawable", packageName)
                            image_view.setImageResource(resourceId)
                            image_view.visibility = View.VISIBLE
                        }
                        else{
                            currentPiece --
                        }
                        secondcodeScanner()
                    }
                }
            )

        }
    }

    private val colorVerificationLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val color = data?.getStringExtra("color").toString()
            Log.i("MYAPp", "$color color Selected")
            Toast.makeText(this, "Color found : $color" , Toast.LENGTH_SHORT).show()
            colorSelectedListener?.onColorSelected(color)
        }
    }

    fun switchActivity(xA: Int, yA: Int,xB: Int, yB: Int,  color: String , colorSelectedListener: ColorSelectedListener) {
        this.colorSelectedListener = colorSelectedListener
        val intent = Intent(this, colorVerification::class.java)


        intent.putExtra("pieceXA", xA)
        intent.putExtra("pieceYA", yA)

        intent.putExtra("pieceXB", xB)
        intent.putExtra("pieceYB", yB)

        Log.e("MyApp", "cor e 0$xA")
        Log.e("MyApp", "cor e 0$yA")

        Log.e("MyApp", "cor e 0$xB")
        Log.e("MyApp", "cor e 0$yB")
        intent.putExtra("Colour_From_Assembly", color)
        Log.i("MYAPP", "Before")
        colorVerificationLauncher.launch(intent)
        Log.i("MYAPP", "After")

    }

    interface ColorSelectedListener {
        fun onColorSelected(color: String)
    }

    private var colorSelectedListener: ColorSelectedListener? = null

}