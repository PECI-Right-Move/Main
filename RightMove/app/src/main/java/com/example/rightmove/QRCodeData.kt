package com.example.rightmove

data class QRCodeData(
    val id: String,
    val color: String,
    val dimensions: Dimensions,
    val imageName : String
)

data class Dimensions(
    val x: Int,
    val y: Int,
    val z: Int
)
data class Steps(
    val id: String,
    val coordinates : Dimensions,
    val color: String
)
data class Instructions(
    val id: String,
    val step1: Steps,
    val step2: Steps,
    val step3: Steps,
    val step4: Steps,
    val name: String
)