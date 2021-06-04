package com.example.pokedex.ui.favourites

import android.content.Intent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.clear
import coil.load
import com.example.pokedex.R
import com.example.pokedex.databinding.ItemFavouriteBinding
import com.example.pokedex.models.Pokemon
import com.example.pokedex.ui.pokemon.PokemonActivity
import com.example.pokedex.util.Util
import com.example.pokedex.viewmodel.PokemonViewModel

class FavsAdapter(private val favs: MutableList<Pokemon>, private val model: PokemonViewModel, private val fragment: FavouritesFragment) : RecyclerView.Adapter<FavsAdapter.FavsViewHolder>() {

    class FavsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemFavouriteBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favourite, parent, false)
        val fvh = FavsViewHolder(view)
        fvh.binding.imgReorder.setOnTouchListener { view, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                fragment.startDragging(fvh)
            }
            return@setOnTouchListener true

        }
        return fvh
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

        if (model.reorderEnabled.value == true) {
            holder.binding.imgReorder.load(R.drawable.ic_reorder) { size(64) }
        } else {
            holder.binding.imgReorder.clear()
        }

    }

    override fun getItemCount(): Int {
        return favs.size
    }

    fun moveItem(from: Int, to: Int) {
        val fromLocation = favs[from]
        favs.removeAt(from)
        /*if (to < from) {
            values.add(to, fromValue)
        } else {
            values.add(to - 1, fromValue)
        }*/
        favs.add(to, fromLocation)
    }

}