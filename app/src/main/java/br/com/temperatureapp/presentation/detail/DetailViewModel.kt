package br.com.temperatureapp.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.temperatureapp.domain.Temperature
import br.com.temperatureapp.remote.ThermometerApi
import br.com.temperatureapp.remote.dto.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailViewModel(): ViewModel() {

    init {
        viewModelScope.launch {
            val apiValue = ThermometerApi.service.allTemp().toDomain()
            _values.emit(apiValue)
        }
    }

    private val _values = MutableStateFlow(listOf<Temperature>())
    val values = _values.asStateFlow()

    private var inicial = "2000-01-01"
    private var final = "2000-01-01"

    fun setFilter(inicial: String, final: String){
        this.inicial=inicial
        this.final=final
        viewModelScope.launch {
            val apiValue = ThermometerApi.service.getIntervalTemp(
                inicial,
                final
            ).toDomain()
            _values.emit(apiValue)
        }
    }
}