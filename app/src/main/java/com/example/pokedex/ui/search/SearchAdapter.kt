package com.example.pokedex.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.databinding.ItemPokemonBinding
import com.example.pokedex.models.PokemonSearch
import com.example.pokedex.models.PokemonSimple

class SearchAdapter :
    PagedListAdapter<PokemonSimple, SearchAdapter.PokemonSearchViewHolder>(PokemonDiffUtil) {


    class PokemonSearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPokemonBinding.bind(view)
        fun bindPost(pokemon: PokemonSimple) {
            binding.tvNamePokemon.text = pokemon.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonSearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return PokemonSearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonSearchViewHolder, position: Int) {
        getItem(position)?.let { holder.bindPost(it) }
    }

}