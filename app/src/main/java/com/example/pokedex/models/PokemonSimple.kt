package com.example.pokedex.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class PokemonSimple(
        @PrimaryKey
        val name: String,
        val url: String
) : Serializable
