package com.cicerodev.crudcomposeapp.model

data class Cliente(val codigo: Long? = null, var nome: String, var idade: Int?, var cidade: String) {
    constructor() : this(null, "", null,"")
}
