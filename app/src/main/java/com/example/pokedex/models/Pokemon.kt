package com.example.pokedex.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Pokemon(
        val height: Int,
        val id: Int,
        val name: String,
        val order: Int,
        val weight: Int, val sprites: Sprites,
        val stats: List<Stat>,
        val abilities: List<Ability>,
        val types: List<Type>,
        val species: Info
) : Serializable

data class Sprites(val other: SpritesOthers) : Serializable


data class SpritesOthers(
        @SerializedName("official-artwork")
        @Expose
        val officialArtwork: OfficialArtwork
) : Serializable

data class OfficialArtwork(val front_default: String) : Serializable

data class Stat(
        val base_stat: Int,
        val effort: Int,
        val stat: Info) : Serializable

data class Ability(
        val ability: Info,
        val is_hidden: Boolean,
        val slot: Int
) : Serializable

data class Type(
        val slot: Int,
        val type: Info
) : Serializable

data class Info(
        val name: String,
        val url: String) : Serializable




