package com.example.safepass2026_grupo1

// Validación de reglas
fun Int?.esMayorDeEdad(): Boolean = this != null && this >= 18

// Lógica usando scope functions y higher-order functions
fun realizarCheckIn(
    nombre: String,
    edadStr: String,
    tipo: String,
    onValidacionPrioridad: (Asistente) -> Unit
): RegistroState {

    // Validación usando toIntOrNull()
    val edadIngresada = edadStr.toIntOrNull()
        ?: return RegistroState.Error("Edad inválida")

    // let para operaciones si los valores no son nulos
    return nombre.takeIf { it.isNotBlank() }?.let { nombreValido ->
        if (edadIngresada.esMayorDeEdad()) {
            val nuevoAsistente = Asistente(
                nombre = nombreValido,
                edad = edadIngresada,
                tipoEntrada = tipo
            ).apply {
                onValidacionPrioridad(this)
            }

            RegistroState.Success(
                mensaje = "Registro exitoso: ${nuevoAsistente.nombre}.",
                asistente = nuevoAsistente
            )
        } else {
            RegistroState.Error("El asistente debe ser mayor de edad.")
        }
    } ?: RegistroState.Error("El nombre no puede estar vacío.")
}