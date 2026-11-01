package com.example.balneapp.ui.theme.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.balneapp.R
import com.example.balneapp.navigation.AppRoute
import com.example.balneapp.ui.viewmodel.PerfilViewModel
import com.example.balneapp.ui.viewmodel.balneAppViewModel

@Composable
fun PantallaRegistro(
    navController: NavController,
    viewModel: PerfilViewModel = balneAppViewModel()
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var mostrarAvisoSocial by remember { mutableStateOf(false) }

    val azulOscuro = Color(0xFF0D3767)
    val celesteAgua = Color(0xFF079EAD)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDF8F4))
    )

     {
        Image(
            painter = painterResource(id = R.drawable.decoracion2),
            contentDescription = "Decoración de fondo",
            modifier = Modifier
                .height(170.dp)
                .align(Alignment.TopStart)
                .padding(top = 100.dp),
            contentScale = ContentScale.FillHeight
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))


            Image(
                painter = painterResource(id = R.drawable.logobalneapp),
                contentDescription = "Logo BalneApp",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(color = Color(0xFFF3EFE6), shape = CircleShape)
                        .clickable { navController.popBackStack() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "‹",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = celesteAgua
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Creá tu cuenta",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = azulOscuro
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Nombre completo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = fullName,
                    onValueChange = { fullName = it; error = "" },
                    label = { Text("Nombre completo") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Correo electrónico",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = email,
                    onValueChange = { email = it; error = "" },
                    label = { Text("Ejemplo@correo.com") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Contraseña",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it; error = "" },
                    label = { Text("Mínimo 6 caracteres") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Confirmá tu contraseña",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it; error = "" },
                    label = { Text("Reescribe la contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (error.isNotBlank()) {
                Text(
                    text = error,
                    color = Color(0xFFBD3D3D),
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
                )
            }

            Button(
                onClick = {
                    val correo = email.trim()
                    when {
                        fullName.isBlank() -> error = "Ingresá tu nombre completo."
                        !correo.matches(Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) -> error = "Ingresá un correo electrónico válido."
                        password.length < 6 -> error = "La contraseña debe tener al menos 6 caracteres."
                        password != confirmPassword -> error = "Las contraseñas no coinciden."
                        else -> viewModel.registrarCuenta(fullName.trim(), correo) {
                            navController.navigate(AppRoute.Inicio.route) {
                                popUpTo(AppRoute.Login.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = celesteAgua)
            ) {
                Text(
                    text = "Registrarse",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "— o continuá con —",
                    fontSize = 12.sp,
                    color = Color.Blue
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = { mostrarAvisoSocial = true },
                    modifier = Modifier.weight(1f).height(46.dp),
                    shape = RoundedCornerShape(11.dp)
                ) { Text("G  Google", color = Color(0xFF333333), fontSize = 13.sp) }
                OutlinedButton(
                    onClick = { mostrarAvisoSocial = true },
                    modifier = Modifier.weight(1f).height(46.dp),
                    shape = RoundedCornerShape(11.dp)
                ) { Text("f  Facebook", color = Color(0xFF333333), fontSize = 13.sp) }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿Ya tenés cuenta?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = azulOscuro
                )
                Text(
                    text = "Iniciá sesión",
                    fontSize = 14.sp,
                    color = celesteAgua,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (mostrarAvisoSocial) {
        AlertDialog(
            onDismissRequest = { mostrarAvisoSocial = false },
            title = { Text("Registro con redes") },
            text = { Text("El registro con Google o Facebook estará disponible cuando conectemos el proveedor de cuentas.") },
            confirmButton = { TextButton(onClick = { mostrarAvisoSocial = false }) { Text("Entendido") } }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaRegistroPreview() {
    PantallaRegistro(navController = rememberNavController())
}
