package br.com.temperatureapp.remote.dto

import br.com.temperatureapp.domain.Temperature

data class TemperatureDto(
    val localizacao: String,
    val sensorId: Int,
    val temperatura: Float,
    val umidade: Float,
    val timeStamp: String
)

fun TemperatureDto.toDomain(): Temperature{
    return Temperature (
        sensorId = this.sensorId,
        temperatura = this.temperatura,
        umidade = this.umidade,
        localizacao = this.localizacao,
        data = this.timeStamp.substring(0,10),
        horario = this.timeStamp.substring(11,19)
    )
}

fun List<TemperatureDto>.toDomain(): List<Temperature>{
    return map{
        Temperature(
            sensorId = it.sensorId,
            temperatura = it.temperatura,
            umidade = it.umidade,
            localizacao = it.localizacao,
            data = it.timeStamp.substring(0,10),
            horario = it.timeStamp.substring(11,19)
        )
    }
}