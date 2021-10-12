package com.example.mergeanimalsandflowers.presentation.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mergeanimalsandflowers.R
import com.example.mergeanimalsandflowers.databinding.FragmentSearchBinding
import com.example.mergeanimalsandflowers.domain.models.AnimalAndFlowerMergedModel
import com.example.mergeanimalsandflowers.presentation.home.AnimalAndFlowerMergedAdapter
import com.example.mergeanimalsandflowers.utils.Keyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: AnimalAndFlowerMergedAdapter
    private lateinit var fragmentSearchBinding: FragmentSearchBinding
    private lateinit var searchView: SearchView
    private var queryValue = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return fragmentSearchBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        setupFoodAdapter()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted{
            viewModel.searchPageState.collect { searchPageState ->
                    handleVisibilityViews(searchPageState)
                    when (searchPageState) {
                        is SearchPageState.DataFetched -> { showList(searchPageState.mergedList) }
                        is SearchPageState.EmptyData -> { showMessage(searchPageState.message) }
                    }
                }
        }
    }

    private fun setupFoodAdapter() {
        adapter = AnimalAndFlowerMergedAdapter(::navigateToDetailsFragment)
        fragmentSearchBinding.rvAnimalsAndFlowers.adapter = adapter
    }

    private fun showList(foods: List<AnimalAndFlowerMergedModel>) {
        adapter.submitList(foods)
    }

    private fun showMessage(message: String) {
        fragmentSearchBinding.txtNoItemFound.text = message
    }

    private fun handleVisibilityViews(searchPageState: SearchPageState) {
        fragmentSearchBinding.apply {
            txtNoItemFound.isVisible = searchPageState is SearchPageState.EmptyData
            rvAnimalsAndFlowers.isVisible = searchPageState is SearchPageState.DataFetched
        }
    }

    private fun navigateToDetailsFragment(animalAndFlowerMergedModel: AnimalAndFlowerMergedModel) {
        Keyboard.closeKeyboard(requireActivity())
        SearchFragmentDirections
            .actionSearchFragmentToDetailsFragment(animalAndFlowerMergedModel)
            .apply { findNavController().navigate(this) }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        searchView = menu.findItem(R.id.menu_search).actionView as SearchView
        searchView.apply {
            setIconifiedByDefault(false)
            requestFocusFromTouch()
            isFocusable = true
            isIconified = false
            maxWidth = Integer.MAX_VALUE
            queryHint = getString(R.string.searchFa)
            if (queryValue.isNotEmpty()){
                setQuery(queryValue, false)
            }
            setOnQueryTextListener(searchWatcher)
        }
    }

    private val searchWatcher = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?) = false

        override fun onQueryTextChange(newText: String): Boolean {
            queryValue = newText.trim()
            viewModel.search(queryValue)
            return false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}