package com.example.sample

data class QRCodeData(
    val id: String,
    val color: String,
    val dimensions: Dimensions,
    val name : String
)

data class Dimensions(
    val x: Int,
    val y: Int
)

data class Step(
    var idPiece: String,
    val idStep: String,
    var color: String,
    val coordinates: Dimensions,
    var dimensions: String
)

data class Instruction(
    val assembly: String,
    val steps : Array<Step>,
)

