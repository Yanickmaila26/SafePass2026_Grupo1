package com.example.safepass2026_grupo1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.safepass2026_grupo1.ui.theme.SafePass2026_Grupo1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilita Edge-to-Edge para cumplir con el estándar de la API 36
        enableEdgeToEdge()
        setContent {
            SafePass2026_Grupo1Theme {
                // Composable principal requerido por el examen
                SafePassApp()
            }
        }
    }
}

@Composable
fun SafePassApp() {
    // Estados para controlar los campos de entrada y la UI
    var nombreInput by remember { mutableStateOf("") }
    var edadInput by remember { mutableStateOf("") }
    var estadoRegistro by remember { mutableStateOf<RegistroState>(RegistroState.Idle) }

    // Implementación obligatoria de Scaffold
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        // Uso de Column para organizar los elementos verticalmente
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "SafePass 2026: Registro",
                style = MaterialTheme.typography.headlineMedium
            )

            // Campos de texto para la entrada de datos
            OutlinedTextField(
                value = nombreInput,
                onValueChange = { nombreInput = it },
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = edadInput,
                onValueChange = { edadInput = it },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    // Llama a la lógica que definiste en LogicaRegistro.kt
                    estadoRegistro = realizarCheckIn(
                        nombre = nombreInput,
                        edadStr = edadInput,
                        tipo = "General",
                        onValidacionPrioridad = { asistente ->
                            // Aquí se aplica la lógica de prioridad o descuento
                            println("Procesando prioridad para: ${asistente.nombre}")
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Asistente")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Uso de when como expresión exhaustiva para renderizar la UI según el estado
            when (val actual = estadoRegistro) {
                is RegistroState.Idle -> {
                    Text("Esperando ingreso de datos...")
                }
                is RegistroState.Success -> {

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Estado: EXITOSO", style = MaterialTheme.typography.titleMedium)
                            Text(text = "Mensaje: ${actual.mensaje}")
                            Text(text = "Asistente: ${actual.asistente.nombre}")
                            Text(text = "Tipo: ${actual.asistente.tipoEntrada}")
                        }
                    }
                }
                is RegistroState.Error -> {
                    //Mensaje de error
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Error: ${actual.error}",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}