package fr.isen.sintoni.isensmartcompanion

import android.content.Context
import android.content.SharedPreferences

class ReminderManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("event_reminders", Context.MODE_PRIVATE)

    // Enregistre le rappel associé à l'évènement
    fun saveReminder(eventId: String) {
        sharedPreferences.edit().putBoolean("reminder_$eventId", true).apply()
    }

    // Supprime le rappel
    fun removeReminder(eventId: String) {
        sharedPreferences.edit().remove("reminder_$eventId").apply()
    }

    // Vérifie si le rappel est activé
    fun isReminderSet(eventId: String): Boolean {
        return sharedPreferences.getBoolean("reminder_$eventId", false)
    }
}
