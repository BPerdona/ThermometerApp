package br.com.temperatureapp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.temperatureapp.remote.ThermometerApi
import br.com.temperatureapp.remote.dto.toDomain
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class HomeViewModel(): ViewModel() {

    private val _currentDevice: MutableStateFlow<Int> = MutableStateFlow(1)
    val currentDevice = _currentDevice.asStateFlow()

    private val _thermometer = flow{
        while (true){
            try{
                val aux = ThermometerApi.service.getTemp(_currentDevice.value)
                emit(aux.toDomain())
            }catch (e: Exception){
                Log.e("flow", "Erro ao conectar API: ${e.message}")
                e.printStackTrace()
            }
            delay(1000L)
        }
    }
    val thermometer = _thermometer


    fun altDevice(){
        when(_currentDevice.value){
            1 -> _currentDevice.update { 2 }
            2 -> _currentDevice.update { 1 }
        }
    }
}