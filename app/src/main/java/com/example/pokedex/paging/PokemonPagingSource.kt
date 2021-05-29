package com.example.pokedex.paging

import androidx.paging.PageKeyedDataSource
import androidx.paging.TiledDataSource
import com.example.pokedex.models.PokemonSearch
import com.example.pokedex.models.PokemonSimple
import com.example.pokedex.networking.ApiClient
import com.example.projekt1.networking.APIService
import com.example.projekt1.networking.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PokemonPagingSource(
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, PokemonSimple>() {

    private val apiService = ApiClient.getClient().create(APIService::class.java)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PokemonSimple>
    ) {

        scope.launch {
            println("LAUNCHING SCOPE")
            val response = apiService.getPokemonSearch(20, 0)
            println("GOT RESPONSE + " + response)
            when {
                response.isSuccessful -> {
                    println("SUCCESSFUL RESPONSE")
                    val listing = response.body()?.results
                    if (listing != null) {
                        callback.onResult(listing, null, 20)
                    }
                }
            }
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonSimple>) {
        println("PARAMS KEY " + params.key)
        scope.launch {
            val response = apiService.getPokemonSearch(20, params.key)
            when {
                response.isSuccessful -> {

                    val listing = response.body()?.results
                    if (listing != null) {
                        callback.onResult(listing, params.key + 20)
                    }
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonSimple>) {
        println("LOAD AFTER  " + params.key)
        scope.launch {
            val response = apiService.getPokemonSearch(20, params.key)
            when {
                response.isSuccessful -> {
                    val listing = response.body()?.results
                    if (listing != null) {
                        println(params.key.toString() + "AAAAAAAAAAAAAA" + listing.map { it -> it.name })
                        callback.onResult(listing, params.key + 20)
                    }
                }
            }
        }
    }

}