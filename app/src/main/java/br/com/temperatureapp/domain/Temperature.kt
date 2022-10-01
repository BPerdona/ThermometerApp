package br.com.temperatureapp.domain

data class Temperature(
    val sensorId: Int,
    val localizacao: String,
    val temperatura: Float,
    val umidade: Float,
    val data: String,
    val horario: String
)
