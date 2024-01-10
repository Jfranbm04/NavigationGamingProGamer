package com.example.navegasionkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navegasionkotlin.ui.theme.NavegasionKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavegasionKotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF2196F3) // Color primario (azul)
                ) {
                    SportsApp()
                }
            }
        }
    }
}

@Composable
fun SportsApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.SportList.route) {
        composable(Screen.SportList.route) {
            SportListScreen(navController = navController)
        }
        composable(Screen.SportDetails.route) { backStackEntry ->
            val sportId = backStackEntry.arguments?.getString("sportId")
            if (sportId != null) {
                SportDetailsScreen(navController = navController, sportId = sportId)
            } else {
                // Manejo del caso en que el sportId es nulo o no válido
                Text(
                    text = "Error: ID de deporte no válido.",
                    color = Color.Red,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun SportListScreen(navController: NavController) {
    val sports = listOf(
        Pair("Fútbol", R.drawable.futbol),
        Pair("Baloncesto", R.drawable.baloncesto),
        Pair("Tenis", R.drawable.tenis),
        Pair("Golf", R.drawable.golf),
        Pair("Ciclismo", R.drawable.ciclismo)
    )
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(sports) { (sport, imageId) ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("${Screen.SportDetails.route.replace("{sportId}", sport)}")
                    }
                    .padding(16.dp)
            ) {
                Text(
                    text = sport,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = sport,
                    modifier = Modifier
                    //    .size(500.dp) // Tamaño de la imagen
                    //    .clip(shape = CircleShape) // Forma circular
                    //    .background(MaterialTheme.colorScheme.primary) // Fondo circular del mismo color que el tema
                        .align(Alignment.CenterHorizontally)
                )

            }
        }

    }
}

@Composable
fun SportDetailsScreen(navController: NavController, sportId: String) {
    // Puedes utilizar el sportId para obtener información detallada del deporte
    val sportInfo = getSportInfo(sportId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = sportInfo,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp) // Añade espacio en la parte inferior del texto
        )

        Spacer(modifier = Modifier.weight(1f)) // Spacer con weight para empujar el botón hacia abajo

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF4CAF50)) // Color de fondo verde
        ) {
            Text(text = "Volver a la lista de deportes", color = Color.White)
        }
    }
}


fun getSportInfo(sportId: String): String {
    return when (sportId) {
        "Fútbol" -> "El fútbol es un deporte en el que dos equipos compiten por marcar más goles que el otro."
        "Baloncesto" -> "El baloncesto es un deporte en el que dos equipos compiten por encestar la pelota en el aro del equipo contrario."
        "Tenis" -> "El tenis es un deporte de raqueta en el que los jugadores compiten golpeando la pelota sobre la red."
        "Golf" -> "El golf es un deporte en el que los jugadores intentan golpear la pelota en el menor número de golpes posible."
        "Ciclismo" -> "El ciclismo es un deporte en el que los participantes compiten en carreras de bicicletas."
        else -> "Información no disponible para $sportId."
    }
}

