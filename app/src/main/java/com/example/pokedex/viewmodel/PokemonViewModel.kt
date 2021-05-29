package com.example.pokedex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pokedex.models.Pokemon
import com.example.pokedex.models.PokemonSearch
import com.example.pokedex.models.PokemonSimple
import com.example.pokedex.paging.PokemonPagingSource
import java.lang.AssertionError

class PokemonViewModel : ViewModel() {

    var pokemonLiveData: LiveData<PagedList<Pokemon>>
    val hints = MutableLiveData<ArrayList<String>>()


    init {
        val searchConfig =
                PagedList.Config.Builder().setPageSize(20).setEnablePlaceholders(false).build()
        pokemonLiveData = initializedPagedListBuilder(searchConfig).build()
        hints.value = pokemonLiveData.value?.map { it -> it.name } as ArrayList<String>?
    }

    fun getPokemons(): LiveData<PagedList<Pokemon>> = pokemonLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, Pokemon> {

        val dataSourceFactory = object : DataSource.Factory<Int, Pokemon>() {
            override fun create(): DataSource<Int, Pokemon> {
                return PokemonPagingSource(viewModelScope)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

}