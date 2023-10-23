package com.cicerodev.crudcomposeapp.connection

import com.cicerodev.crudcomposeapp.model.Cliente
import retrofit2.Response

class ClientRepository(private val apiService: ApiService) {
    suspend fun getClientes(): Response<List<Cliente>> {
        return apiService.getClientes()
    }

    suspend fun getHello(): String {
        return apiService.getHello()
    }

}