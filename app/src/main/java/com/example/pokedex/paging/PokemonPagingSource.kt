package com.example.pokedex.paging

import androidx.paging.PageKeyedDataSource
import androidx.paging.TiledDataSource
import com.example.pokedex.models.Pokemon
import com.example.pokedex.models.PokemonSearch
import com.example.pokedex.models.PokemonSimple
import com.example.pokedex.networking.ApiClient
import com.example.projekt1.networking.APIService
import com.example.projekt1.networking.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class PokemonPagingSource(
        private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Pokemon>() {

    private val apiService = ApiClient.getClient().create(APIService::class.java)

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, Pokemon>
    ) {

        scope.launch {
            println("LAUNCHING SCOPE")
            val waitingResponse = async { apiService.getPokemonSearch(20, 0) }
            val response = waitingResponse.await()
            val waitingPokemon = response.body()?.results?.map {
                async {
                    apiService.getPokemon(it.url.substring(18))
                }
            }

            val pokemons = waitingPokemon?.awaitAll()
            when {
                response.isSuccessful -> {

                    if (pokemons != null) {
                        callback.onResult(pokemons, null, 20)
                    }

                }
            }
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Pokemon>) {
        println("PARAMS KEY " + params.key)
        scope.launch {
            println("LAUNCHING SCOPE")
            val waitingResponse = async { apiService.getPokemonSearch(20, params.key) }
            val response = waitingResponse.await()
            val waitingPokemon = response.body()?.results?.map {
                async {
                    apiService.getPokemon(it.url.substring(18))
                }
            }

            val pokemons = waitingPokemon?.awaitAll()

            when {
                response.isSuccessful -> {

                    if (pokemons != null) {
                        callback.onResult(pokemons, params.key + 20)
                    }

                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Pokemon>) {
        println("LOAD AFTER  " + params.key)
        scope.launch {
            println("LAUNCHING SCOPE")
            val waitingResponse = async { apiService.getPokemonSearch(20, params.key) }
            val response = waitingResponse.await()
            val waitingPokemon = response.body()?.results?.map {
                async {
                    apiService.getPokemon(it.url.substring(18))
                }
            }

            val pokemons = waitingPokemon?.awaitAll()

            when {
                response.isSuccessful -> {

                    if (pokemons != null) {
                        callback.onResult(pokemons, params.key + 20)
                    }

                }
            }
        }
    }

}