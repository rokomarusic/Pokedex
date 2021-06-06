package com.example.pokedex.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.example.pokedex.R
import com.example.pokedex.databinding.DialogLayoutBinding
import com.example.pokedex.models.Pokemon
import com.example.pokedex.viewmodel.PokemonViewModel
import com.google.android.material.snackbar.Snackbar

class ClearDialogFragment(private val model: PokemonViewModel) : DialogFragment() {


    private var _binding: DialogLayoutBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.item_background);
        _binding = DialogLayoutBinding.inflate(inflater, container, false)
        val activity = this.parentFragment



        binding.btnClear.setOnClickListener {
            model.deleteAll(context)
            val snackbar = activity?.view?.let { it1 ->
                Snackbar.make(
                    it1,
                    getString(R.string.fav_is_clear),
                    Snackbar.LENGTH_LONG
                )
            }
            if (snackbar != null) {
                snackbar.animationMode = Snackbar.ANIMATION_MODE_SLIDE
                snackbar.show()
            }
            dialog?.dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}