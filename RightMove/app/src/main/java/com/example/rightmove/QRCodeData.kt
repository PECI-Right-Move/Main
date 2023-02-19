package com.example.rightmove

data class QRCodeData(
    val id: String,
    val color: String,
    val dimensions: Dimensions
)

data class Dimensions(
    val x: Int,
    val y: Int,
    val z: Int
)