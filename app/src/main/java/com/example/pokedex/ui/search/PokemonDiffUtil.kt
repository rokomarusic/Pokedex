package com.example.pokedex.ui.search

import androidx.recyclerview.widget.DiffUtil
import com.example.pokedex.models.PokemonSimple

object PokemonDiffUtil : DiffUtil.ItemCallback<PokemonSimple>() {
    override fun areItemsTheSame(oldItem: PokemonSimple, newItem: PokemonSimple): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: PokemonSimple, newItem: PokemonSimple): Boolean {
        return oldItem.name == newItem.name && oldItem.url == newItem.url
    }

}
