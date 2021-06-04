package com.example.pokedex.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokedex.models.PokemonSimple

@Database(entities = [PokemonSimple::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

}