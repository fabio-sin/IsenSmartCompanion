package fr.isen.sintoni.isensmartcompanion

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun EventsScreen() {
    val context = LocalContext.current

    val eventsState = remember { mutableStateOf<List<Event>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }

    // Coroutine pour récupérer les events
    LaunchedEffect(Unit) {
        try {
            // Appel API
            val events = RetrofitInstance.api.getEvents()
            // Update la liste des events
            eventsState.value = events
            // Change l'état du chargement
            isLoading.value = false
        } catch (e: Exception) {
            // Si erreur => chargement à faux et renvoi erreur
            isLoading.value = false
            Log.e("EventsScreen", "Erreur réseau: $e")
        }
    }

    if (isLoading.value) {
        // Chargement
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(eventsState.value) { event ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(context, EventDetailActivity::class.java)
                            intent.putExtra("event_id", event.id)
                            context.startActivity(intent)
                        },
                    colors = CardDefaults.cardColors(containerColor = Color.Red),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = event.title,
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = event.date, fontSize = 14.sp, color = Color.White)
                        Text(text = event.location, fontSize = 14.sp, color = Color.White)
                    }
                }
            }
        }
    }
}