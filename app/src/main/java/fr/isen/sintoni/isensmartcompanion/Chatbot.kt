package fr.isen.sintoni.isensmartcompanion

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch


class Chatbot(application: Application) : AndroidViewModel(application) {
    private val chatMessageDao = AppDatabase.getDatabase(application).chatMessageDao()

    val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    var messages = mutableStateListOf<String>()
        private set

    // Envoyer un message
    fun sendMessage(userInput: String) {
        messages.add("You: $userInput")
        viewModelScope.launch {
            try {
                val response = generativeModel.generateContent(userInput)
                val aiResponse = response.text ?: "No response"
                messages.add("Companion: $aiResponse")

                // Enregistrement de la réponse dans la bdd
                val chatMessage = ChatMessage(question = userInput, answer = aiResponse, timestamp = System.currentTimeMillis())
                chatMessageDao.insertChatMessage(chatMessage)

            } catch (e: Exception) {
                messages.add("Error: ${e.message}")
            }
        }
    }


    // Messages de la bdd
    var chatHistory = mutableStateListOf<ChatMessage>()
        private set

    // Charger l'historique des ChatMessage
    fun loadChatHistory() {
        viewModelScope.launch {
            try {
                val history = chatMessageDao.getAllChatMessages()
                chatHistory.clear()
                chatHistory.addAll(history)
            } catch (e: Exception) {
                Log.e("Chatbot", "Error loading chat history: ${e.message}")
            }
        }
    }

    // Supprimer un message de l'historique
    fun deleteChatMessage(chatMessage: ChatMessage) {
        viewModelScope.launch {
            chatMessageDao.deleteChatMessage(chatMessage)
            loadChatHistory()
        }
    }

    // Supprimer l'historique entier
    fun deleteAllChatHistory() {
        viewModelScope.launch {
            chatMessageDao.deleteAllChatMessages()
            loadChatHistory()
        }
    }
}