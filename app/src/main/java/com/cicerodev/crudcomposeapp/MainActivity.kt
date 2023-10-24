package com.cicerodev.crudcomposeapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cicerodev.crudcomposeapp.connection.ApiService
import com.cicerodev.crudcomposeapp.connection.RetrofitConfig.apiService
import com.cicerodev.crudcomposeapp.connection.ViewModelScreen
import com.cicerodev.crudcomposeapp.model.Cliente
import com.cicerodev.crudcomposeapp.ui.theme.CRUDCOMPOSEAPPTheme
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : ComponentActivity() {

    private val clienteViewModel: ViewModelScreen by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CRUDCOMPOSEAPPTheme {
                MyScreen(apiService)
            }
            clienteViewModel.fetchAllClients()
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyScreen(apiService: ApiService) {


        var nome by remember { mutableStateOf("") }
        var codigo by remember { mutableStateOf("") }
        var idade by remember { mutableStateOf("") }
        var cidade by remember { mutableStateOf("") }
        var isValidButton by remember { mutableStateOf(false) }
        var clients by remember { mutableStateOf<List<Cliente>>(emptyList()) }
        val coroutineScope = rememberCoroutineScope()

        fun fetchClientsAndHandleErrors() {
            coroutineScope.launch {
                val response: Response<List<Cliente>> = apiService.getClientes()

                if (response.isSuccessful) {
                    clients = response.body() ?: emptyList()
                } else {
                    // Lidar com erros, se necessário
                }
            }
        }

        // Chame a função para buscar os clientes no início do Composable
        fetchClientsAndHandleErrors()

        DisposableEffect(Unit) {
            onDispose { /* Ações de limpeza, se necessário */ }
        }
        fun clearFields() {
            nome = ""
            idade = ""
            cidade = ""
        }


        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Imagem de perfil",
                modifier = Modifier
                    .size(200.dp)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop
            )

            TextField(
                value = nome,
                onValueChange = { newText ->
                    nome = newText
                },
                placeholder = { Text(text = "Nome") }
            )

            TextField(
                value = idade,
                onValueChange = { idade = it },
                placeholder = { Text(text = "Idade") }
            )

            TextField(
                value = cidade,
                onValueChange = { cidade = it },
                placeholder = { Text(text = "Cidade") }
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (!isValidButton) {
                    Button(
                        onClick = {
                            val cliente =
                                Cliente(nome = nome, idade = idade.toInt(), cidade = cidade)
                            clienteViewModel.postClient(cliente)
                            clearFields()
                            fetchClientsAndHandleErrors()
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "Cadastrar")
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Button(
                            onClick = {
                                val newCliente = Cliente(
                                    codigo.toLong(),
                                    nome = nome,
                                    idade = idade.toInt(),
                                    cidade = cidade
                                )
                                newCliente.codigo?.let {
                                    clienteViewModel.putClient(
                                        it,
                                        newCliente
                                    )
                                }
                            },
                        ) {
                            Text(text = "Alterar")
                        }

                        Button(
                            onClick = { /* Ação para Remover */ },
                        ) {
                            Text(text = "Remover")
                        }

                        Button(
                            onClick = { /* Ação para Cancelar */ },
                        ) {
                            Text(text = "Cancelar")
                        }
                    }
                }
            }


            if (!isValidButton) {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(clients) { cliente ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .background(Color.White)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text("Código: ${cliente.codigo}", fontSize = 16.sp)
                                        Text("Nome: ${cliente.nome}", fontSize = 16.sp)
                                        Text("Idade: ${cliente.idade}", fontSize = 16.sp)
                                        Text("Cidade: ${cliente.cidade}", fontSize = 16.sp)
                                        Button(
                                            onClick = {
                                                codigo = cliente.codigo.toString()
                                                nome = cliente.nome
                                                idade = cliente.idade.toString()
                                                cidade = cliente.cidade
                                                isValidButton = true
                                            },
                                        ) {
                                            Text(text = "Selecionar")
                                        }
                                    }
                                }
                            }
                        }
                    }
            }


        }

    }


}