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

data class Step(
    val idPiece: String,
    val idStep: String,
    val color: String,
    val coordinates: Dimensions
)

data class Instruction(
    val assembly: String,
    val steps : Array<Step>,
    val instName: String
)

