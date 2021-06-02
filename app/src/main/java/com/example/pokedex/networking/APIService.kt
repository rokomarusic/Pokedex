package com.example.projekt1.networking


import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.example.pokedex.models.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("/api/v2/pokemon")
    suspend fun getPokemonSearch(
            @Query("limit") limit: Int = 20,
            @Query("offset") offset: Int = 0
    ): Response<PokemonSearch>

    @GET("{pokemon}")
    suspend fun getPokemon(@Path("pokemon") pokemon: String): Pokemon

    @GET("{species}")
    suspend fun getPokemonSpecies(@Path("species") species: String): PokemonSpecies

    @GET("{type}")
    suspend fun getPokemonType(@Path("type") type: String): PokemonType

    @GET("{move}")
    suspend fun getPokemonMove(@Path("move") move: String): PokemonMove

    @GET("{evolution}")
    suspend fun getEvolutionChain(@Path("evolution") evolution: String): PokemonEvolution

    /*@GET("/api/location/search/?query=(query)")
    suspend fun getLocations(@Query("query") name: String): List<LocationResponse>

    @GET("/api/location/{woeid}/")


    @GET("/api/location/{woeid}/{date}/")
    suspend fun getLocationDay(
        @Path("woeid") woeid: String,
        @Path("date") date: String
    ): List<ConsolidatedWeather>*/
}