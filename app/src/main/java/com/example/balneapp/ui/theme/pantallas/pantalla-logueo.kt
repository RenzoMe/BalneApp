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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.balneapp.R
import com.example.balneapp.navigation.AppRoute
import com.example.balneapp.ui.viewmodel.PerfilViewModel
import com.example.balneapp.ui.viewmodel.balneAppViewModel

private val fondoCuenta = Color(0xFFFDF8F4)
private val azulCuenta = Color(0xFF0D3767)
private val turquesaCuenta = Color(0xFF079EAD)

@Composable
fun PantallaLogueo(
    navController: NavController,
    viewModel: PerfilViewModel = balneAppViewModel()
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var error by rememberSaveable { mutableStateOf("") }
    var mostrarAyudaAcceso by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoCuenta)
    ) {
        Image(
            painter = painterResource(R.drawable.decoracion),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(width = 180.dp, height = 130.dp),
            contentScale = ContentScale.Crop,
            alpha = 0.55f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp, vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(18.dp))
            Image(
                painter = painterResource(R.drawable.logobalneapp),
                contentDescription = "BalneApp",
                modifier = Modifier.size(width = 154.dp, height = 126.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(20.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "¡Bienvenido de vuelta!",
                    color = azulCuenta,
                    fontSize = 24.sp,
                    lineHeight = 29.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(3.dp))
                Text("Iniciá sesión para continuar", color = Color(0xFF777777), fontSize = 14.sp)
                Spacer(Modifier.height(25.dp))

                Text("Correo electrónico", color = Color(0xFF222222), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                Spacer(Modifier.height(6.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it; error = "" },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("ejemplo@correo.com", color = Color(0xFFB0B0B0), fontSize = 13.sp) },
                    shape = RoundedCornerShape(12.dp),
                    colors = camposCuenta()
                )
                Spacer(Modifier.height(16.dp))

                Text("Contraseña", color = Color(0xFF222222), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                Spacer(Modifier.height(6.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it; error = "" },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("Ingresá tu contraseña", color = Color(0xFFB0B0B0), fontSize = 13.sp) },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        TextButton(onClick = { passwordVisible = !passwordVisible }) {
                            Text(if (passwordVisible) "Ocultar" else "Ver", color = Color(0xFF777777), fontSize = 11.sp)
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = camposCuenta()
                )

                Text(
                    text = "¿Olvidaste tu contraseña?",
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { mostrarAyudaAcceso = true }
                        .padding(top = 9.dp, bottom = 2.dp),
                    color = turquesaCuenta,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                if (error.isNotBlank()) {
                    Spacer(Modifier.height(6.dp))
                    Text(error, color = Color(0xFFBD3D3D), fontSize = 12.sp)
                }

                Spacer(Modifier.height(18.dp))
                Button(
                    onClick = {
                        val correo = email.trim()
                        if (!correo.matches(Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"))) {
                            error = "Ingresá un correo electrónico válido."
                        } else if (password.length < 6) {
                            error = "La contraseña debe tener al menos 6 caracteres."
                        } else {
                            viewModel.iniciarSesion(correo) {
                                navController.navigate(AppRoute.Inicio.route) {
                                    popUpTo(AppRoute.Login.route) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = turquesaCuenta)
                ) {
                    Text("Iniciar sesión", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                }

                Spacer(Modifier.height(19.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("────────", color = Color(0xFFE0DCD8), fontSize = 11.sp)
                    Text("  o continuá con  ", color = Color(0xFF777777), fontSize = 11.sp)
                    Text("────────", color = Color(0xFFE0DCD8), fontSize = 11.sp)
                }
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = { mostrarAyudaAcceso = true },
                        modifier = Modifier.weight(1f).height(46.dp),
                        shape = RoundedCornerShape(11.dp)
                    ) { Text("G  Google", color = Color(0xFF333333), fontSize = 13.sp) }
                    OutlinedButton(
                        onClick = { mostrarAyudaAcceso = true },
                        modifier = Modifier.weight(1f).height(46.dp),
                        shape = RoundedCornerShape(11.dp)
                    ) { Text("f  Facebook", color = Color(0xFF333333), fontSize = 13.sp) }
                }
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("¿No tenés cuenta?  ", color = Color(0xFF555555), fontSize = 13.sp)
                    Text(
                        "Registrate",
                        modifier = Modifier.clickable { navController.navigate(AppRoute.Registro.route) },
                        color = turquesaCuenta,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }

    if (mostrarAyudaAcceso) {
        AlertDialog(
            onDismissRequest = { mostrarAyudaAcceso = false },
            title = { Text("Acceso a tu cuenta") },
            text = { Text("El acceso con Google, Facebook y la recuperación de contraseña requieren conectar un proveedor de cuentas. Podés registrarte o iniciar sesión con tu correo.") },
            confirmButton = { TextButton(onClick = { mostrarAyudaAcceso = false }) { Text("Entendido") } }
        )
    }
}

@Composable
private fun camposCuenta() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    focusedBorderColor = turquesaCuenta,
    unfocusedBorderColor = Color(0xFFE8E2DE),
    focusedTextColor = Color(0xFF222222),
    unfocusedTextColor = Color(0xFF222222),
    cursorColor = turquesaCuenta
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaLogueoPreview() {
    PantallaLogueo(navController = rememberNavController())
}
