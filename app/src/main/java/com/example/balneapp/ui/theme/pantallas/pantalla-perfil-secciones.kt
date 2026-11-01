package com.example.balneapp.ui.theme.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.balneapp.R
import com.example.balneapp.ui.viewmodel.PerfilViewModel
import com.example.balneapp.ui.viewmodel.balneAppViewModel

private val fondoSecciones = Color(0xFFFDF8F4)
private val turquesaSecciones = Color(0xFF079EAD)
private val textoSecciones = Color(0xFF292929)

@Composable
fun PantallaMisDatos(
    navController: NavController,
    viewModel: PerfilViewModel = balneAppViewModel()
) {
    val preferencias by viewModel.preferencias.collectAsState()
    var nombre by rememberSaveable(preferencias.nombre) { mutableStateOf(preferencias.nombre) }
    var email by rememberSaveable(preferencias.email) { mutableStateOf(preferencias.email) }
    var mensaje by rememberSaveable { mutableStateOf("") }

    PantallaSeccionPerfil(navController, "Mis datos") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text("Mantené actualizada la información de tu cuenta.", color = Color(0xFF777777), fontSize = 14.sp)
            TarjetaContenido {
                CampoPerfil("Nombre completo", nombre, { nombre = it; mensaje = "" }, "Tu nombre")
                Spacer(Modifier.height(12.dp))
                CampoPerfil("Correo electrónico", email, { email = it; mensaje = "" }, "ejemplo@correo.com")
            }
            if (mensaje.isNotBlank()) Text(mensaje, color = Color(0xFFBD3D3D), fontSize = 12.sp)
            BotonPrimario("Guardar cambios") {
                when {
                    nombre.isBlank() -> mensaje = "Ingresá tu nombre completo."
                    !email.trim().matches(Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) -> mensaje = "Ingresá un correo válido."
                    else -> {
                        viewModel.guardarPerfil(nombre, email) { navController.popBackStack() }
                    }
                }
            }
        }
    }
}

@Composable
fun PantallaMetodosPago(
    navController: NavController,
    viewModel: PerfilViewModel = balneAppViewModel()
) {
    val preferencias by viewModel.preferencias.collectAsState()
    var mostrarFormulario by rememberSaveable { mutableStateOf(false) }
    var marca by rememberSaveable { mutableStateOf("Visa") }
    var ultimosDigitos by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf("") }

    PantallaSeccionPerfil(navController, "Métodos de pago") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Administrá los medios de pago de tu cuenta.", color = Color(0xFF777777), fontSize = 14.sp)
            if (preferencias.tarjetasGuardadas.isEmpty()) {
                TarjetaContenido {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.metodospago),
                            contentDescription = null,
                            modifier = Modifier.size(42.dp)
                        )
                        Spacer(Modifier.width(13.dp))
                        Column {
                            Text("Todavía no agregaste una tarjeta", color = textoSecciones, fontWeight = FontWeight.SemiBold)
                            Spacer(Modifier.height(3.dp))
                            Text("Podés guardar una referencia para tus reservas.", color = Color(0xFF777777), fontSize = 12.sp)
                        }
                    }
                }
                BotonPrimario("Agregar tarjeta") { mostrarFormulario = true }
            } else {
                preferencias.tarjetasGuardadas.forEach { tarjeta ->
                    TarjetaContenido {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(R.drawable.metodospago),
                                contentDescription = null,
                                modifier = Modifier.size(42.dp)
                            )
                            Spacer(Modifier.width(13.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(tarjeta.substringBefore(" ••••"), color = textoSecciones, fontWeight = FontWeight.SemiBold)
                                Text("•••• ${tarjeta.substringAfter("•••• ", "")}", color = Color(0xFF777777), fontSize = 13.sp)
                            }
                            TextButton(onClick = { viewModel.quitarTarjeta(tarjeta) }) { Text("Quitar", color = Color(0xFFD84C4C)) }
                        }
                    }
                }
                BotonPrimario("Agregar otra tarjeta") {
                    ultimosDigitos = ""
                    error = ""
                    mostrarFormulario = true
                }
            }
        }
    }

    if (mostrarFormulario) {
        AlertDialog(
            onDismissRequest = { mostrarFormulario = false },
            title = { Text("Agregar tarjeta") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Guardamos solo la marca y los últimos cuatro dígitos.", color = Color(0xFF777777), fontSize = 13.sp)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("Visa", "Mastercard", "Otra").forEach { opcion ->
                            FilterChip(
                                selected = marca == opcion,
                                onClick = { marca = opcion },
                                label = { Text(opcion, fontSize = 11.sp) }
                            )
                        }
                    }
                    OutlinedTextField(
                        value = ultimosDigitos,
                        onValueChange = { nuevo ->
                            ultimosDigitos = nuevo.filter(Char::isDigit).take(4)
                            error = ""
                        },
                        label = { Text("Últimos 4 dígitos") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = campoColores()
                    )
                    if (error.isNotBlank()) Text(error, color = Color(0xFFBD3D3D), fontSize = 12.sp)
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (ultimosDigitos.length != 4) error = "Ingresá los cuatro dígitos."
                    else {
                        viewModel.guardarTarjeta(marca, ultimosDigitos)
                        mostrarFormulario = false
                    }
                }) { Text("Guardar", color = turquesaSecciones) }
            },
            dismissButton = { TextButton(onClick = { mostrarFormulario = false }) { Text("Cancelar") } }
        )
    }
}

@Composable
fun PantallaConfiguracion(
    navController: NavController,
    viewModel: PerfilViewModel = balneAppViewModel()
) {
    val preferencias by viewModel.preferencias.collectAsState()
    PantallaSeccionPerfil(navController, "Configuración") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Elegí cómo querés recibir novedades de BalneApp.", color = Color(0xFF777777), fontSize = 14.sp)
            TarjetaContenido {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Notificaciones", color = textoSecciones, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(4.dp))
                        Text("Avisos sobre reservas y novedades", color = Color(0xFF777777), fontSize = 12.sp)
                    }
                    Switch(
                        checked = preferencias.notificacionesHabilitadas,
                        onCheckedChange = viewModel::establecerNotificaciones
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaAyuda(navController: NavController) {
    var preguntaAbierta by rememberSaveable { mutableStateOf(-1) }
    val preguntas = listOf(
        "¿Cómo hago una reserva?" to "Elegí un balneario, consultá su disponibilidad, seleccioná el servicio y confirmá el resumen.",
        "¿Dónde encuentro mis reservas?" to "Están disponibles en la opción Mis reservas de tu perfil y en la barra inferior.",
        "¿Cómo guardo un balneario en Favoritos?" to "Tocá el corazón que aparece en la tarjeta o en el detalle del balneario.",
        "¿Cómo actualizo mis datos?" to "Desde Perfil, entrá en Mis datos, editá la información y guardá los cambios."
    )

    PantallaSeccionPerfil(navController, "Ayuda") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Preguntas frecuentes", color = Color(0xFF777777), fontSize = 14.sp)
            preguntas.forEachIndexed { indice, (pregunta, respuesta) ->
                TarjetaContenido {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { preguntaAbierta = if (preguntaAbierta == indice) -1 else indice }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(pregunta, modifier = Modifier.weight(1f), color = textoSecciones, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Text(if (preguntaAbierta == indice) "−" else "+", color = turquesaSecciones, fontSize = 22.sp)
                        }
                        if (preguntaAbierta == indice) {
                            Spacer(Modifier.height(10.dp))
                            Text(respuesta, color = Color(0xFF666666), fontSize = 13.sp, lineHeight = 19.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PantallaSeccionPerfil(
    navController: NavController,
    titulo: String,
    contenido: @Composable () -> Unit
) {
    Scaffold(containerColor = fondoSecciones) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(start = 18.dp, end = 20.dp, top = 18.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF3EFE6))
                        .clickable { navController.popBackStack() },
                    contentAlignment = Alignment.Center
                ) {
                    Text("‹", color = turquesaSecciones, fontSize = 28.sp, fontWeight = FontWeight.Medium)
                }
                Spacer(Modifier.width(13.dp))
                Text(titulo, color = textoSecciones, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }
            Box(Modifier.fillMaxSize()) { contenido() }
        }
    }
}

@Composable
private fun TarjetaContenido(contenido: @Composable () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) { contenido() }
    }
}

@Composable
private fun CampoPerfil(
    titulo: String,
    valor: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column {
        Text(titulo, color = textoSecciones, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(5.dp))
        OutlinedTextField(
            value = valor,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text(placeholder, color = Color(0xFFB0B0B0), fontSize = 13.sp) },
            shape = RoundedCornerShape(11.dp),
            colors = campoColores()
        )
    }
}

@Composable
private fun campoColores() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    focusedBorderColor = turquesaSecciones,
    unfocusedBorderColor = Color(0xFFE8E2DE),
    focusedTextColor = textoSecciones,
    unfocusedTextColor = textoSecciones,
    cursorColor = turquesaSecciones
)

@Composable
private fun BotonPrimario(texto: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = turquesaSecciones)
    ) {
        Text(texto, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaAyudaPreview() {
    PantallaAyuda(navController = rememberNavController())
}
