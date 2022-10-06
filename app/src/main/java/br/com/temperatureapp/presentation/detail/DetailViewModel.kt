package br.com.temperatureapp.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.temperatureapp.remote.ThermometerApi
import br.com.temperatureapp.remote.dto.toDomain
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class DetailViewModel(): ViewModel() {

    private val _values = flow {
       while (true) {
           try {
               emit(ThermometerApi.service.getAllTemp(1,"$inicial 01:01:01","$final 01:01:01").toDomain())
           } catch (e: Exception) {
               Log.e("flow", "Erro ao consumir lista com paramentros $inicial e $final\nError: ${e.message}")
           }
           delay(500L)
       }
    }
    val values = _values
    private var inicial = "2000-01-01"
    private var final = "2000-01-01"

    fun setFilter(inicial: String, final: String){
        this.inicial=inicial
        this.final=final
    }
}