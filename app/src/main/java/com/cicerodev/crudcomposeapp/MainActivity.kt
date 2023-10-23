package com.cicerodev.crudcomposeapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
//                ClientListScreen(apiService)
            }
            clienteViewModel.fetchAllClients()
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyScreen(apiService: ApiService) {

        val viewModel: ViewModelScreen by viewModels()


        var nome by remember { mutableStateOf("") }
        var idade by remember { mutableStateOf("") }
        var cidade by remember { mutableStateOf("") }
        var isValidButton by remember { mutableStateOf(false) }
        var clients by remember { mutableStateOf<List<Cliente>>(emptyList()) }
        val coroutineScope = rememberCoroutineScope()

        DisposableEffect(Unit) {
            coroutineScope.launch {
                val response: Response<List<Cliente>> = apiService.getClientes()

                if (response.isSuccessful) {
                    clients = response.body() ?: emptyList()
                } else {
                }
            }

            onDispose { }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Imagem de perfil",
                modifier = Modifier
                    .size(200.dp)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = nome,
                onValueChange = { newText ->
                    nome = newText
                },
                placeholder = { Text(text = "Nome") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = idade,
                onValueChange = { idade = it },
                placeholder = { Text(text = "Idade") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = cidade,
                onValueChange = { cidade = it },
                placeholder = { Text(text = "Cidade") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (!isValidButton) {
                    Button(
                        onClick = { /* Ação para Cadastrar */ },
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
                            onClick = { /* Ação para Alterar */ },
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

            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
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
                                    onClick = { isValidButton = true },
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