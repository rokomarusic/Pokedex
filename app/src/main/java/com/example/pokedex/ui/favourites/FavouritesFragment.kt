package com.example.pokedex.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentFavouritesBinding
import com.example.pokedex.databinding.FragmentSettingsBinding
import com.example.pokedex.viewmodel.PokemonViewModel

class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null

    private val binding get() = _binding!!

    private val model: PokemonViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        model.getFavourites(requireContext())

        binding.list.layoutManager = LinearLayoutManager(requireContext())

        if (model.favourites.value.isNullOrEmpty()) {
            model.favourites.observe(viewLifecycleOwner, {
                val adapter = FavsAdapter(it, model)
                binding.list.adapter = adapter
            })
        }



        model.favourites.observe(viewLifecycleOwner, {
            if (binding.list.adapter == null) {
                model.favourites.observe(viewLifecycleOwner, {
                    val adapter = FavsAdapter(it, model)
                    binding.list.adapter = adapter
                })
            } else {
                //binding.list.adapter?.notifyDataSetChanged()
            }
        })

        return binding.root
    }
}