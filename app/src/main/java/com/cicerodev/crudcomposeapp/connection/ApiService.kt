package com.cicerodev.crudcomposeapp.connection

import com.cicerodev.crudcomposeapp.model.Cliente
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("/clientes")
    suspend fun getClientes(): Response<List<Cliente>>
    @POST("/cliente")
    suspend fun postCliente(@Body cliente: Cliente): Response<Cliente>
    @GET("/hello")
    suspend fun getHello(): String
}