package fr.isen.sintoni.isensmartcompanion

import android.content.Context
import android.content.SharedPreferences

class ReminderManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("event_reminders", Context.MODE_PRIVATE)

    // Fonction pour enregistrer l'état du rappel
    fun saveReminder(eventId: String) {
        // Ajoute un rappel pour l'événement
        sharedPreferences.edit().putBoolean("reminder_$eventId", true).apply()
    }

    // Fonction pour supprimer l'état du rappel
    fun removeReminder(eventId: String) {
        // Supprime la clé correspondante pour l'événement
        sharedPreferences.edit().remove("reminder_$eventId").apply()
    }

    // Fonction pour vérifier si un rappel est activé pour cet événement
    fun isReminderSet(eventId: String): Boolean {
        // Retourne true si le rappel est activé, sinon false
        return sharedPreferences.getBoolean("reminder_$eventId", false)
    }
}
