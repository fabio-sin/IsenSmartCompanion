package fr.isen.sintoni.isensmartcompanion

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.sintoni.isensmartcompanion.ui.theme.IsenSmartCompanionTheme

val eventState = mutableStateOf<Event?>(null)
private val isLoading = mutableStateOf(true)


class EventDetailActivity : ComponentActivity() {

    private var reminderManager: ReminderManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val eventId = intent.getStringExtra("event_id") ?: return

        reminderManager = ReminderManager(this)
        val isReminderSet = reminderManager?.isReminderSet(eventId) ?: false
        Log.d("EventDetailActivity", "Event ID: $eventId, Reminder: ${isReminderSet.toString()}")


        setContent {
            IsenSmartCompanionTheme {
                LaunchedEffect(Unit) {
                    try {
                        val events = RetrofitInstance.api.getEvents()
                        val event = events.find { it.id == eventId }
                        if (event != null) {
                            eventState.value = event
                        } else {
                            showError("Event not found")
                        }
                    } catch (e: Exception) {
                        showError("Error: ${e.message}")
                    } finally {
                        isLoading.value = false
                    }
                }

                // Affichage du contenu selon l'état
                val event = eventState.value
                if (isLoading.value) {
                    CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                } else if (event != null) {
                    EventDetailScreen(event, isReminderSet)
                } else {
                    showError("Event not found")
                }
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}


@Composable
fun EventDetailScreen(event: Event, isReminderSet: Boolean) {
    val context = LocalContext.current
    val reminderManager = remember { ReminderManager(context) }

    val hasReminderState = remember { mutableStateOf(isReminderSet) }

    val updateReminderState = { newState: Boolean ->
        if (newState) {
            reminderManager.saveReminder(event.id)
        } else {
            reminderManager.removeReminder(event.id)
        }
        hasReminderState.value = newState
        Log.d("ReminderState", "Reminder state updated: ${hasReminderState.value}")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.Red),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = event.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                InfoRow(label = context.getString(R.string.when_label), value = event.date)
                InfoRow(label = context.getString(R.string.where_label), value = event.location)
                InfoRow(
                    label = context.getString(R.string.category_label), value = event.category
                )
                InfoRow(
                    label = context.getString(R.string.description_label), value = event.description
                )

                IconButton(
                    onClick = { updateReminderState(!hasReminderState.value) },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Icon(
                        imageVector = if (hasReminderState.value) Icons.Default.Notifications else Icons.Outlined.Notifications,
                        contentDescription = context.getString(R.string.reminder),
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.LightGray
        )
        Text(
            text = value, fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
}