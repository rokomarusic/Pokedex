package com.example.pokedex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pokedex.models.PokemonSearch
import com.example.pokedex.models.PokemonSimple
import com.example.pokedex.paging.PokemonPagingSource
import java.lang.AssertionError

class PokemonViewModel : ViewModel() {

    var pokemonSimpleLiveData: LiveData<PagedList<PokemonSimple>>


    init {
        val searchConfig =
            PagedList.Config.Builder().setPageSize(20).setEnablePlaceholders(false).build()
        pokemonSimpleLiveData = initializedPagedListBuilder(searchConfig).build()
    }

    fun getPokemons(): LiveData<PagedList<PokemonSimple>> = pokemonSimpleLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, PokemonSimple> {

        val dataSourceFactory = object : DataSource.Factory<Int, PokemonSimple>() {
            override fun create(): DataSource<Int, PokemonSimple> {
                return PokemonPagingSource(viewModelScope)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

}