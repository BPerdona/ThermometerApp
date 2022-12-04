package br.com.temperatureapp.remote.dto

import br.com.temperatureapp.domain.Temperature

data class TemperatureDto(
    val temperature: Float,
    val moisture: Float,
    val date: String,
    val time: String,
)

fun TemperatureDto.toDomain(): Temperature{
    return Temperature (
        temperatura = this.temperature,
        umidade = this.moisture,
        data = this.date,
        horario = this.time.substring(0,8)
    )
}

fun List<TemperatureDto>.toDomain(): List<Temperature>{
    return map{
        it.toDomain()
    }
}