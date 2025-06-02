// app/src/main/java/com/example/myapplication/presentation/CharactersFragment.kt
package com.example.myapplication.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCharactersBinding
import com.example.myapplication.presentaition.ui.adapters.CharactersAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharactersFragment : Fragment(com.example.myapplication.R.layout.fragment_characters) {

    companion object {
        @JvmStatic
        fun newInstance(): CharactersFragment {
            return CharactersFragment()
        }
    }
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharactersViewModel by viewModels()
    private val adapter = CharactersAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharactersBinding.bind(view)

        binding.recyclerCharacters.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CharactersFragment.adapter
        }

        // Подписываемся на список персонажей
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { list ->
                if (list.isNotEmpty()) {
                    adapter.submitList(list)
                    binding.recyclerCharacters.visibility = View.VISIBLE
                } else {
                    binding.recyclerCharacters.visibility = View.GONE
                }
            }
        }

        // Подписываемся на индикатор загрузки
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                if (isLoading) {
                    binding.recyclerCharacters.visibility = View.GONE
                    binding.textError.visibility = View.GONE
                }
            }
        }

        // Подписываемся на ошибки
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorMessage.collect { error ->
                if (error != null) {
                    binding.textError.visibility = View.VISIBLE
                    binding.textError.text = error
                    binding.recyclerCharacters.visibility = View.GONE
                } else {
                    binding.textError.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
