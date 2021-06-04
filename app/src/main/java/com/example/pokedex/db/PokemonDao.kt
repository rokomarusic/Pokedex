package com.example.pokedex.db

import androidx.room.*
import com.example.pokedex.models.PokemonSimple

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemon(pokemonSimple: PokemonSimple)

    @Delete
    suspend fun deletePokemon(pokemonSimple: PokemonSimple)

    @Query("SELECT * FROM PokemonSimple")
    suspend fun getFavourites(): List<PokemonSimple>

    @Query("DELETE FROM PokemonSimple")
    suspend fun deleteAll()
}