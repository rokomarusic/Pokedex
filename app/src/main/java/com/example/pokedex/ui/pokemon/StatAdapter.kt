package com.example.pokedex.ui.pokemon

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.databinding.ItemAbilityBinding
import com.example.pokedex.databinding.ItemStatBinding
import com.example.pokedex.models.Stat
import com.example.pokedex.util.Util

class StatAdapter(private val stats: MutableList<Stat>) : RecyclerView.Adapter<StatAdapter.StatViewHolder>() {

    class StatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemStatBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stat, parent, false)
        return StatViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
        val stat = stats[position]
        holder.binding.tvStatValue.text = stat.base_stat.toString()
        if (stat.stat.url == "") {
            holder.binding.tvStatName.text = holder.itemView.resources.getString(R.string.total)
            holder.binding.tvStatName.setTextColor(holder.itemView.resources.getColor(R.color.cold_gray, holder.itemView.resources.newTheme()))
            holder.binding.tvStatValue.setTextColor(holder.itemView.resources.getColor(R.color.cold_gray, holder.itemView.resources.newTheme()))
            holder.binding.progressBar.visibility = View.INVISIBLE
        } else {
            holder.binding.tvStatName.text = holder.itemView.resources.getString(Util.getStatName(stat.stat.name))
            holder.binding.progressBar.progressDrawable.setTint(ContextCompat.getColor(holder.itemView.context, Util.getStatColor(stat.stat.name)))
            holder.binding.progressBar.progress = stat.base_stat
        }

    }

    override fun getItemCount(): Int {
        return stats.size
    }

}