package com.riopermana.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * An interface defines the contract for making network requests to a API endpoint
 * backed by Retrofit.
 */
interface ApiService {

    /**
     * Fetches CSV file of list of active or de-listed US stocks and ETFs,
     * either as of the latest trading day or at a specific time in history.
     *
     * @param apiKey The API key for authentication.
     * @return [ResponseBody]
     */
    @GET("/query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String,
    ) : ResponseBody

}