package com.example.pokedex.type

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.databinding.TypePokemonBinding
import com.example.pokedex.models.Info
import com.example.pokedex.util.Util

class TypeAdapter(private val types: MutableList<Info>) : RecyclerView.Adapter<TypeAdapter.TypeViewHolder>() {

    class TypeViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding = TypePokemonBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.type_pokemon, parent, false)
        return TypeViewHolder(view)
    }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        val type = types[position]
        holder.binding.tvTypeName.text = type.name
        holder.binding.root.background.setTint(ContextCompat.getColor(holder.itemView.context, Util.getTypeColor(type.name)))
    }

    override fun getItemCount(): Int {
        return types.size
    }


}