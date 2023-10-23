package com.cicerodev.crudcomposeapp.connection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cicerodev.crudcomposeapp.model.Cliente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelScreen() : ViewModel() {
    val retrofitConfig = RetrofitConfig
    val apiService: ApiService = retrofitConfig.apiService
    var hello: String = ""

    fun fetchAllClients() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                apiService.getClientes()
            } catch (e: Exception) {
                // Lidar com erros aqui
            }
        }
    }

     fun getHello() {
        viewModelScope.launch {
            hello = apiService.getHello()
        }
    }
}