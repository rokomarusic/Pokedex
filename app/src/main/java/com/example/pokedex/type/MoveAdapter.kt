package com.example.pokedex.type

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.databinding.ItemMoveRowBinding
import com.example.pokedex.models.PokemonMove
import com.example.pokedex.util.Util

class MoveAdapter(private val moves: MutableList<PokemonMove>) : RecyclerView.Adapter<MoveAdapter.MoveViewHolder>() {

    class MoveViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMoveRowBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoveViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_move_row, parent, false)
        return MoveViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoveViewHolder, position: Int) {
        val move = moves[position]
        println("MOVE " + position + " " + move)
        holder.binding.categoryValue.tvMoveItemValue.text = move.damage_class?.name?.let { Util.capitalizeFirstLetter(it) }
        holder.binding.genValue.tvMoveItemValue.text = Util.getGenNumber(move.generation.name)
        holder.binding.moveValue.tvMoveItemValue.text = Util.getMoveName(move.name)
        holder.binding.powerValue.tvMoveItemValue.text = move.power.toString()
        holder.binding.ppValue.tvMoveItemValue.text = move.pp.toString()

        //holder.binding.genValue.root.background.setTint(ContextCompat.getColor(holder.itemView.context, Util.getGenColor(move.generation.name)))
        holder.binding.genValue.root.setBackgroundColor(holder.itemView.resources.getColor(Util.getGenColor(move.generation.name)))
        holder.binding.genValue.root.background.alpha = 77

        move.damage_class?.name?.let { Util.getCategoryColor(it) }?.let { holder.itemView.resources.getColor(it) }?.let { holder.binding.categoryValue.root.setBackgroundColor(it) }
        holder.binding.categoryValue.root.background.alpha = 77
    }

    override fun getItemCount(): Int {
        return moves.size
    }
}