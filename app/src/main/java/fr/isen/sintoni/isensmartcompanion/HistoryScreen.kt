package fr.isen.sintoni.isensmartcompanion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
            text = context.getString(R.string.history),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        // Bouton pour supprimer tout l'historique
        Button(
            onClick = {
                viewModel.deleteAllChatHistory()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text(text = context.getString(R.string.delete_history), color = Color.White)
        }


        // Affichage des messages chargés dans l'historique
        LazyColumn(
            modifier = Modifier.padding(8.dp)
        ) {
            items(viewModel.chatHistory) { message ->
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = "You: ${message.question}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Companion: ${message.answer}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Date: ${
                            java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                                .format(java.util.Date(message.timestamp))
                        }", fontSize = 12.sp, fontWeight = FontWeight.Bold
                    )

                    Button(
                        onClick = {
                            viewModel.deleteChatMessage(message)
                        },
                        modifier = Modifier.align(Alignment.End),
                        colors = ButtonDefaults.buttonColors(Color.Red)
                    ) {
                        Text(text = context.getString(R.string.delete), color = Color.White)
                    }

                    HorizontalDivider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }

}
