package br.com.temperatureapp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.temperatureapp.remote.ThermometerApi
import br.com.temperatureapp.remote.dto.toDomain
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class HomeViewModel(): ViewModel() {

    private val _thermometer = flow{
        while (true){
            try{
                val aux = ThermometerApi.service.getTemp(1)
                emit(aux.toDomain())
            }catch (e: Exception){
                Log.e("flow", "Erro ao conectar API")
            }
            delay(500L)
        }
    }
    val thermometer = _thermometer
}