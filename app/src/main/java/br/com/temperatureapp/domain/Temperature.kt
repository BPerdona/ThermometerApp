package br.com.temperatureapp.domain

data class Temperature(
    val temperatura: Float,
    val umidade: Float,
    val data: String,
    val horario: String
)
