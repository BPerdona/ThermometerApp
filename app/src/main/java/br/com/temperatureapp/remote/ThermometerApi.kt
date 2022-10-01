package br.com.temperatureapp.remote

import br.com.temperatureapp.remote.dto.TemperatureDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "http://10.0.2.2:8080"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ThermometerService{

    @GET("temperatura/{id}")
    suspend fun getTemp(@Path("id")id: Int): TemperatureDto

    @GET("temperatura/{id}/2021-01-01 01:01:01/to/2023-01-01 01:01:01")
    suspend fun getAllTemp(@Path("id")id: Int): List<TemperatureDto>
}

object ThermometerApi{
    val service: ThermometerService by lazy{
        retrofit.create(ThermometerService::class.java)
    }
}