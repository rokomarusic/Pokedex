package com.example.pokedex.viewmodel

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pokedex.db.DatabaseBuilder
import com.example.pokedex.models.*
import com.example.pokedex.networking.ApiClient
import com.example.pokedex.paging.PokemonPagingSource
import com.example.pokedex.util.Util
import com.example.projekt1.networking.APIService
import com.example.projekt1.networking.RetrofitBuilder
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.AssertionError

class PokemonViewModel : ViewModel() {

    var pokemonLiveData: LiveData<PagedList<Pokemon>>
    val hints = MutableLiveData<ArrayList<Pokemon>>()
    val evolutionMap = MutableLiveData<Map<Int, MutableList<Pokemon>>>()
    val minLvlMap = MutableLiveData<Map<Int, MutableList<Int?>>>()
    val type1 = MutableLiveData<PokemonType>()
    val type2 = MutableLiveData<PokemonType>()
    val moves = MutableLiveData<ArrayList<PokemonMove>>()
    val favourites = MutableLiveData<ArrayList<Pokemon>>()
    val reorderEnabled = MutableLiveData<Boolean>()


    init {
        val searchConfig =
                PagedList.Config.Builder().setPageSize(20).setEnablePlaceholders(false).build()
        pokemonLiveData = initializedPagedListBuilder(searchConfig).build()
        reorderEnabled.value = false
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

    fun getEvolutionChain(species: String) {
        viewModelScope.launch {
            println("SPECIES STRING " + species.substring(18))
            val waitingSpecies = async { RetrofitBuilder.apiService.getPokemonSpecies(species.substring(18)) }
            val species = waitingSpecies.await()
            println("SPECIES " + species)
            val waitingChain = async { RetrofitBuilder.apiService.getEvolutionChain(species.evolution_chain.url.substring(18)) }
            val temp = waitingChain.await()
            println("EVOLUŠN ČEIN " + temp)
            var chain = temp.chain.evolves_to
            println("CHAIN " + chain)
            val evolutionList = HashMap<Int, MutableList<Info>>()
            val minLevelMap = HashMap<Int, MutableList<Int?>>()
            for (i in chain.indices) {
                var next = chain
                minLevelMap[i] = mutableListOf()
                evolutionList[i] = mutableListOf()
                evolutionList[i]?.add(temp.chain.species)
                while (next.isNotEmpty()) {
                    minLevelMap[i]?.add(next[i].evolution_details[0].min_level)
                    evolutionList[i]?.add(next[i].species)
                    next = next[i].evolves_to
                }
            }

            minLvlMap.value = minLevelMap

            val mapOfEvolutions = HashMap<Int, MutableList<Pokemon>>()

            for (i in evolutionList.keys) {
                mapOfEvolutions[i] = mutableListOf()
                for (info in evolutionList[i]!!) {
                    val waitingSpecies = async { RetrofitBuilder.apiService.getPokemonSpecies(info.url.substring(18)) }
                    val species = waitingSpecies.await()
                    val waitingPokemon = async { RetrofitBuilder.apiService.getPokemon(species.varieties[0].pokemon.url.substring(18)) }
                    mapOfEvolutions[i]?.add(waitingPokemon.await())
                }
            }

            evolutionMap.value = mapOfEvolutions
        }
    }

    fun getType(type: String, no: Int) {
        viewModelScope.launch {
            val waitingType = async { RetrofitBuilder.apiService.getPokemonType(type.substring(18)) }
            val type = waitingType.await()
            if (no == 1) {
                type1.value = type
            } else {
                type2.value = type
            }
        }
    }

    fun getMoves(movesList: List<Info>) {
        viewModelScope.launch {
            val waitingMoves = movesList.map {
                async {
                    RetrofitBuilder.apiService.getPokemonMove(it.url.substring(18))
                }
            }
            moves.value = waitingMoves.awaitAll() as ArrayList<PokemonMove>
        }

    }

    fun getFavourites(context: Context?) {
        viewModelScope.launch {
            if (context != null) {
                val waitingPokemonSimple = async {
                    DatabaseBuilder.getInstance(context).pokemonDao().getFavourites() as MutableList<PokemonSimple>
                }
                val pokemonSimples = waitingPokemonSimple.await()
                val waitingPokemons = pokemonSimples.map {
                    async {
                        RetrofitBuilder.apiService.getPokemon(it.url.substring(18))
                    }
                }
                val temp = waitingPokemons.awaitAll()
                if (temp.isNotEmpty()) {
                    for (item in temp) {
                        item.isFavourite = true
                    }
                    favourites.value = temp as ArrayList<Pokemon>
                }
            }
        }
    }

    fun deleteAll(context: Context?) {
        viewModelScope.launch {
            if (context != null) {
                val waiting =
                    async { DatabaseBuilder.getInstance(context).pokemonDao().deleteAll() }
                val temp = waiting.await()
                favourites.value?.clear()
                println("TTTTTTTTT " + temp + ", " + favourites.value)

                favourites.postValue(favourites.value)
            }
        }
    }

    fun deletePokemon(pokemon: Pokemon, context: Context?) {
        viewModelScope.launch {
            if (context != null) {
                DatabaseBuilder.getInstance(context).pokemonDao().deletePokemon(Util.getPokemonSimple(pokemon, favourites.value))
            }
        }
    }

    fun insertPokemon(pokemon: Pokemon, context: Context?) {
        viewModelScope.launch {
            if (context != null) {
                println("FAVVALUE" + favourites.value)
                DatabaseBuilder.getInstance(context).pokemonDao().insertPokemon(Util.getPokemonSimple(pokemon, favourites.value))
            }
        }
    }

    fun updatePokemons(pokemons: List<Pokemon>, context: Context?) {
        viewModelScope.launch {
            if (context != null) {
                val waitingUpdate = pokemons.mapIndexed { index, pokemon ->
                    async { DatabaseBuilder.getInstance(context).pokemonDao().updatePokemon(Util.getPokemonSimpleForUpdate(pokemon, pokemons, index)) }
                }
                val temp = waitingUpdate.awaitAll()
            }
        }
    }

    fun getHints(name: String) {
        viewModelScope.launch {
            val waitingPokemonSimple = async {
                RetrofitBuilder.apiService.getPokemonSearch(1200, 0)
            }
            var pokemonSimples = waitingPokemonSimple.await().body()?.results as MutableList<PokemonSimple>
            pokemonSimples = pokemonSimples?.filter { it.name.startsWith(name) } as MutableList<PokemonSimple>
            val waitingPokemons = pokemonSimples?.map {
                async {
                    RetrofitBuilder.apiService.getPokemon(it.url.substring(18))
                }
            }
            val temp = waitingPokemons?.awaitAll()
            if (temp?.isNotEmpty() == true) {
                for (item in temp) {
                    item.isFavourite = true
                }
                hints.value = temp as ArrayList<Pokemon>
            }
        }
    }

}