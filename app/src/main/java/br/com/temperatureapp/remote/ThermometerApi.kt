package br.com.temperatureapp.remote

import br.com.temperatureapp.remote.dto.TemperatureDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "http://perdona.pythonanywhere.com/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ThermometerService{

    @GET("{id}/lastTemp")
    suspend fun getTemp(
        @Path("id")id: Int,
    ): TemperatureDto

    @GET("tempIn/{initial}/{final}")
    suspend fun getIntervalTemp(
        @Path("initial")initial: String,
        @Path("final")final: String
    ): List<TemperatureDto>

    @GET("allTemp")
    suspend fun allTemp(): List<TemperatureDto>
}

object ThermometerApi{
    val service: ThermometerService by lazy{
        retrofit.create(ThermometerService::class.java)
    }
}