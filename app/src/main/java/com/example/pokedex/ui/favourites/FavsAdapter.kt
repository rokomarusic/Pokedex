package com.example.pokedex.ui.favourites

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokedex.R
import com.example.pokedex.databinding.ItemFavouriteBinding
import com.example.pokedex.models.Pokemon
import com.example.pokedex.ui.pokemon.PokemonActivity
import com.example.pokedex.util.Util
import com.example.pokedex.viewmodel.PokemonViewModel

class FavsAdapter(private val favs: MutableList<Pokemon>, private val model: PokemonViewModel) : RecyclerView.Adapter<FavsAdapter.FavsViewHolder>() {

    class FavsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemFavouriteBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favourite, parent, false)
        return FavsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavsViewHolder, position: Int) {
        val pokemon = favs[position]
        holder.binding.tvNamePokemon.text = Util.capitalizeFirstLetter(pokemon.name)
        holder.binding.pokemonImg.load(pokemon.sprites.other.officialArtwork.front_default) { size(256) }
        holder.binding.tvOrderPokemon.text = Util.returnId(pokemon.order)

        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.itemView.context, PokemonActivity::class.java)
            intent.putExtra("EXTRA_POKEMON", pokemon)
            holder.itemView.context.startActivity(intent)
        }

        if (model.favourites.value?.let { Util.isFavourite(pokemon, it) } == true) {
            holder.binding.favImg.load(R.drawable.ic_star_1) { size(64) }
        } else {
            holder.binding.favImg.load(R.drawable.ic_star_0) { size(64) }
        }

        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.itemView.context, PokemonActivity::class.java)
            intent.putExtra("EXTRA_POKEMON", pokemon)
            holder.itemView.context.startActivity(intent)
        }

        holder.binding.favImg.setOnClickListener {
            if (pokemon.isFavourite) {
                holder.binding.favImg.load(R.drawable.ic_star_0)
                model.deletePokemon(pokemon, holder.itemView.context)
            } else {
                holder.binding.favImg.load(R.drawable.ic_star_1)
                model.insertPokemon(pokemon, holder.itemView.context)
            }
            pokemon.isFavourite = !pokemon.isFavourite
        }
    }

    override fun getItemCount(): Int {
        return favs.size
    }
}