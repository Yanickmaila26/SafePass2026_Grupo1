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
    val edadIngresada = edadStr.toIntOrNull() ?: 0

    // let para operaciones si los valores no son nulos
    return nombre.takeIf { it.isNotBlank() }?.let { nombreValido ->
        if (edadIngresada.esMayorDeEdad()) {
            val nuevoAsistente = Asistente(nombreValido, edadIngresada, tipo).apply {
                onValidacionPrioridad(this)
            }
            RegistroState.Success(
                asistente = nuevoAsistente,
                mensaje = "Registro exitoso: ${nuevoAsistente.nombre}."
            )
        } else {
            RegistroState.Error("El asistente debe ser mayor de edad.")
        }
    } ?: RegistroState.Error("El nombre no puede estar vacío.") // Elvis para el estado de error
}

