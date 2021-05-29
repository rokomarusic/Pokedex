package com.example.pokedex.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.databinding.FragmentSearchBinding
import com.example.pokedex.databinding.FragmentSettingsBinding
import com.example.pokedex.viewmodel.PokemonViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    private var firstSelection = true

    private val binding get() = _binding!!

    private val model: PokemonViewModel by activityViewModels()

    private val adapter = SearchAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        val view = binding.root

        observeLiveData()
        initializeList()



        return view
    }

    private fun initializeList() {
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
    }

    private fun observeLiveData() {
        model.getPokemons().observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }
}