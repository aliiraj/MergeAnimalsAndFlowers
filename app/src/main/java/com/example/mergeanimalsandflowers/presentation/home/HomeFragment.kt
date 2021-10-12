package com.example.mergeanimalsandflowers.presentation.home

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mergeanimalsandflowers.R
import com.example.mergeanimalsandflowers.databinding.FragmentHomeBinding
import com.example.mergeanimalsandflowers.domain.models.AnimalAndFlowerMergedModel
import com.example.mergeanimalsandflowers.utils.ShowMessageUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: AnimalAndFlowerMergedAdapter
    private lateinit var fragmentHomeBinding: FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.homePageState.collect { homePageState ->
                handleVisibilityViews(homePageState)
                when (homePageState) {
                    is HomePageState.DataFetched -> showList(homePageState.mergedList)
                    is HomePageState.EmptyData -> {
                        showMessage(homePageState.message)
                    }
                    is HomePageState.Error -> {
                        showMessage(homePageState.message)
                    }
                    is HomePageState.RefreshError -> {
                        ShowMessageUtil.showSnackBar(
                            fragmentHomeBinding.swipeRefreshLayout,
                            homePageState.message
                        )
                    }
                    is HomePageState.Refreshing -> {
                        showList(homePageState.mergedList)
                    }
                }
            }
        }
    }

    private fun handleVisibilityViews(homePageState: HomePageState) {
        fragmentHomeBinding.apply {
            swipeRefreshLayout.isRefreshing = homePageState is HomePageState.Refreshing
            loadingAnim.isVisible = homePageState is HomePageState.Loading
            btnTryAgain.isVisible = homePageState is HomePageState.Error
            txtMessage.isVisible = homePageState is HomePageState.Error
        }
    }

    private fun setupFoodAdapter() {
        adapter = AnimalAndFlowerMergedAdapter(::navigateToDetailsFragment)
        fragmentHomeBinding.rvAnimalsAndFlowers.adapter = adapter
    }

    private fun showList(mergedList: List<AnimalAndFlowerMergedModel>) {
        adapter.submitList(mergedList)
    }

    private fun showMessage(message: String) {
        fragmentHomeBinding.txtMessage.text = message
    }

    private fun navigateToDetailsFragment(animalAndFlowerMergedModel: AnimalAndFlowerMergedModel) {
        HomeFragmentDirections.actionHomeFragmentToDetailsFragment(animalAndFlowerMergedModel)
            .apply { findNavController().navigate(this) }
    }

    private fun navigateToSearchFragment() {
        HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            .apply { findNavController().navigate(this) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_search)
            navigateToSearchFragment()

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
