package fr.isen.sintoni.isensmartcompanion

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object RetrofitInstance {
    private const val BASE_URL =
        "https://isen-smart-companion-default-rtdb.europe-west1.firebasedatabase.app/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: EventApi = retrofit.create(EventApi::class.java)
}

interface EventApi {
    @GET("events.json")
    suspend fun getEvents(): List<Event>
}
