package com.example.pokedex.type.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentMovesBinding
import com.example.pokedex.models.PokemonType
import com.example.pokedex.type.MoveAdapter
import com.example.pokedex.viewmodel.PokemonViewModel


class MovesFragment(val type: PokemonType) : Fragment() {

    private var _binding: FragmentMovesBinding? = null

    private val model: PokemonViewModel by activityViewModels()


    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovesBinding.inflate(inflater, container, false)

        model.getMoves(type.moves)

        binding.movelist.layoutManager = LinearLayoutManager(requireContext())

        //postavljanje imena stupaca
        binding.header.headerCategory.tvHeaderName.text = resources.getString(R.string.category)
        binding.header.headerGen.tvHeaderName.text = resources.getString(R.string.gen)
        binding.header.headerMove.tvHeaderName.text = resources.getString(R.string.move)
        binding.header.headerPower.tvHeaderName.text = resources.getString(R.string.power)
        binding.header.headerPp.tvHeaderName.text = resources.getString(R.string.pp)



        model.moves.observe(viewLifecycleOwner, {
            val adapter = MoveAdapter(it)
            binding.movelist.adapter = adapter
        })
        return binding.root
    }

}