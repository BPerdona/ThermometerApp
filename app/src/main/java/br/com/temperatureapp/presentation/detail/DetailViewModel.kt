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
               emit(ThermometerApi.service.getAllTemp(1).toDomain())
           } catch (e: Exception) {
               Log.e("flow", "Erro ao consumir lista")
           }
           delay(1000L)
       }
    }
    val values = _values
}