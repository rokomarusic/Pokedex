package com.example.pokedex.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
                val adapter = FavsAdapter(it, model, this)
                binding.list.adapter = adapter
            })
        }



        model.favourites.observe(viewLifecycleOwner, {
            if (binding.list.adapter == null) {
                model.favourites.observe(viewLifecycleOwner, {
                    val adapter = FavsAdapter(it, model, this)
                    binding.list.adapter = adapter
                })
            } else {
                //binding.list.adapter?.notifyDataSetChanged()
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.list)

        binding.imgEdit.setOnClickListener {
            if (binding.list.adapter != null) {
                model.reorderEnabled.value = !model.reorderEnabled.value!!
                binding.list.adapter!!.notifyDataSetChanged()
            }
        }

        model.reorderEnabled.observe(viewLifecycleOwner, {
            println("EVEEVOEVO")
            binding.list.adapter?.notifyDataSetChanged()
        })


        return binding.root
    }


    fun startDragging(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    private val itemTouchHelper by lazy {
        // 1. Note that I am specifying all 4 directions.
        //    Specifying START and END also allows
        //    more organic dragging than just specifying UP and DOWN.
        val simpleItemTouchCallback =
                object : ItemTouchHelper.SimpleCallback(
                        ItemTouchHelper.UP or
                                ItemTouchHelper.DOWN or
                                ItemTouchHelper.START or
                                ItemTouchHelper.END, 0
                ) {

                    override fun onMove(
                            recyclerView: RecyclerView,
                            viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder
                    ): Boolean {

                        val adapter = recyclerView.adapter as FavsAdapter
                        val from = viewHolder.adapterPosition
                        val to = target.adapterPosition
                        // 2. Update the backing model. Custom implementation in
                        //    MainRecyclerViewAdapter. You need to implement
                        //    reordering of the backing model inside the method.
                        adapter.moveItem(from, to)
                        // 3. Tell adapter to render the model update.
                        adapter.notifyItemMoved(from, to)
                        return true
                    }

                    override fun onSwiped(
                            viewHolder: RecyclerView.ViewHolder,
                            direction: Int
                    ) {
                        // 4. Code block for horizontal swipe.
                        //    ItemTouchHelper handles horizontal swipe as well, but
                        //    it is not relevant with reordering. Ignoring here.
                    }
                }
        ItemTouchHelper(simpleItemTouchCallback)
    }

}