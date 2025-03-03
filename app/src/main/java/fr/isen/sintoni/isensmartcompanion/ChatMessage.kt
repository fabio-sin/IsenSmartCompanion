package fr.isen.sintoni.isensmartcompanion

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_messages")
data class ChatMessage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val question: String,
    val answer: String,
    // date heure actuelle
    val timestamp: Long = System.currentTimeMillis()
)
