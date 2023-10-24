package com.cicerodev.crudcomposeapp.connection

import com.cicerodev.crudcomposeapp.model.Cliente
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("/clientes")
    suspend fun getClientes(): Response<List<Cliente>>

    @POST("/cliente")
    suspend fun postCliente(@Body cliente: Cliente): Response<Cliente>

    @PUT("/cliente/{codigo}")
    suspend fun putCliente(
        @Path(value = "codigo") codigo: Long,
        @Body cliente: Cliente
    ): Response<Cliente>

    @GET("/hello")
    suspend fun getHello(): String
}