package com.example.mergeanimalsandflowers.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mergeanimalsandflowers.data.models.AnimalAndFlowerMergedModel
import com.example.mergeanimalsandflowers.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: AnimalAndFlowerMergedAdapter
    private lateinit var fragmentHomeBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
        return fragmentHomeBinding.root
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
        // visibility views is Handled in xml
        viewModel.homePageState.observe(viewLifecycleOwner) {
            it.let { homePageState ->
                when (homePageState) {
                    is HomePageState.DataFetched -> showList(homePageState.foods)
                    is HomePageState.EmptyData -> showMessage(homePageState.message)
                    is HomePageState.Error -> showMessage(homePageState.message)
                }
            }
        }
    }

    private fun setupFoodAdapter() {
        adapter = AnimalAndFlowerMergedAdapter(::navigateToDetailsFragment)
        fragmentHomeBinding.rvAnimalsAndFlowers.adapter = adapter
    }

    private fun showList(foods: List<AnimalAndFlowerMergedModel>) {
        adapter.submitList(foods)
    }

    private fun showMessage(message: String) {
        fragmentHomeBinding.txtMessage.text = message
    }

    private fun navigateToDetailsFragment(animalAndFlowerMergedModel: AnimalAndFlowerMergedModel) {
        val detailsFragmentDirection = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(animalAndFlowerMergedModel)
        findNavController().navigate(detailsFragmentDirection)
    }
}