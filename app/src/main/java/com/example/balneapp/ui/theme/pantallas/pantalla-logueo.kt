package com.example.balneapp.ui.theme.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.balneapp.R

@Composable
fun PantallaLogueo(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val azulColor = Color(0xFF0D075E)
    val celesteAgua = Color(0xFF00A3C4)
    // box que contiene la pantalla
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        //Imagen de decoracion arriba a la derecha
        Image(
            painter = painterResource(id = R.drawable.decoracion),
            contentDescription = "Decoración de fondo",
            modifier = Modifier
                .height(230.dp)
                .align(Alignment.TopEnd)
                .padding(top = 100.dp),
            contentScale = ContentScale.FillHeight
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            //logo de balneapp
            Image(
                painter = painterResource(id = R.drawable.logobalneapp),
                contentDescription = "Logo",
                modifier = Modifier.size(140.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            // texto de bienvenida
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "¡Bienvenido de vuelta!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = azulColor
                )

                Text(
                    text = "Iniciá sesión para continuar",
                    fontSize = 14.sp,
                    color = celesteAgua
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            //campo tipo input de correo
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Correo electrónico", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Ejemplo@correo.com") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                //campo tipo input de contraseña
                Text(text = "Contraseña", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Ingresá tu contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            // texto de olvidastew tu contraseña?
            Text(
                text = "¿Olvidaste tu contraseña?",
                fontSize = 12.sp,
                color = celesteAgua,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { /* Acción que aun no funciona */ }
            )

            Spacer(modifier = Modifier.height(24.dp))
            //boton de inicio de sesion que lleva a la pantalla principal
            Button(
                onClick = {
                    navController.navigate("principal") {
                        popUpTo("logueo") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = celesteAgua)
            ) {
                Text(text = "Iniciar sesión", fontSize = 16.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))
            //texto de complemento hacia la opcion de google
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "— o continuá con —",
                    fontSize = 14.sp,
                    color = Color.Blue
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            //logo de google que ejemplifica la forma de iniciar sesion en google /luego debemos cambiarlo
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google",
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))
            // texto de ayuda a registrarse
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿No tenés cuenta?",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = "Registrate",
                    fontSize = 14.sp,
                    color = celesteAgua,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate("registro")
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaLogueoPreview() {
    PantallaLogueo(navController = rememberNavController())
}