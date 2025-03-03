package fr.isen.sintoni.isensmartcompanion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HistoryScreen(viewModel: Chatbot = viewModel()) {
    val context = LocalContext.current

    // Charger les messages depuis la base de données
    LaunchedEffect(true) {
        viewModel.loadChatHistory()
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Historique des échanges",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Affichage des messages chargés dans l'historique
        LazyColumn(
            modifier = Modifier.padding(8.dp)
        ) {
            items(viewModel.chatHistory) { message ->
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = "You: ${message.question}", fontSize = 16.sp
                    )
                    Text(
                        text = "Companion: ${message.answer}", fontSize = 16.sp
                    )
                    Text(
                        text = "Date: ${
                            java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                                .format(java.util.Date(message.timestamp))
                        }", fontSize = 12.sp
                    )
                }
            }
        }
    }

}
