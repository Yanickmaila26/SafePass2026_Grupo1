package com.example.safepass2026_grupo1

// Validación de reglas
fun Int?.esMayorDeEdad(): Boolean = this != null && this >= 18

// Lógica usando scope funtions y higher order functions
fun realizarCheckIn(
    nombre: String,
    edadStr: String,
    tipo: String,
    onValidacionPrioridad: (Asistente) -> Unit
): RegistroState {

    // Validación usando toIntOrNull()
    val edadIngresada = edadStr.toIntOrNull()

    // let para operaciones si los valores no son nulos
    return if (nombre.isNotBlank() && edadIngresada.esMayorDeEdad()) {
        val nuevoAsistente = Asistente(nombre, edadIngresada, tipo).apply {
            onValidacionPrioridad(this)
        }
        RegistroState.Success(
            asistente = nuevoAsistente,
            mensaje = "Registro exitoso: ${nuevoAsistente.nombre} (${nuevoAsistente.tipoEntrada})."
        )
    } else {
        RegistroState.Error("Datos inválidos: el nombre no puede estar vacío y debe ser mayor de edad.")
    }
}