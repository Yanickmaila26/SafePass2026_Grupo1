package com.example.safepass2026_grupo1

data class Asistente(
    val nombre: String,
    val edad: Int,
    val tipoEntrada: String
)

//Gestión de Estados con Sealed Classes
sealed class RegistroState {
    object Idle : RegistroState() // Estado inicial / Esperando
    data class Success(val asistente: Asistente, val mensaje: String) : RegistroState() // Registro exitoso
    data class Error(val error: String) : RegistroState() // Problemas de validación
}