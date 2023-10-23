package com.cicerodev.crudcomposeapp.connection

import com.cicerodev.crudcomposeapp.model.Cliente
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/clientes")
    suspend fun getClientes(): Response<List<Cliente>>
    @GET("/hello")
    suspend fun getHello(): String
}