package fr.isen.sintoni.isensmartcompanion

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChatMessageDao {

    @Insert
    suspend fun insertChatMessage(chatMessage: ChatMessage)

    // On récupère tous les messages et on les ordonne par ordre chronologique
    @Query("SELECT * FROM chat_messages ORDER BY timestamp DESC")
    suspend fun getAllChatMessages(): List<ChatMessage>

    @Delete
    suspend fun deleteChatMessage(chatMessage: ChatMessage)

    @Query("DELETE FROM chat_messages")
    suspend fun deleteAllChatMessages()
}