package com.example.mergeanimalsandflowers.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mergeanimalsandflowers.databinding.FragmentDetailsBinding
import com.example.domain.models.AnimalAndFlowerMergedModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var fragmentDetailsBinding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            item = args.mergedModel
        }
        return fragmentDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleSlider(args.mergedModel)
    }

    private fun handleSlider(animalAndFlowerMergedModel: AnimalAndFlowerMergedModel) {
        val sliderAdapter = SliderAdapter(getImageAnimalAndFlower(animalAndFlowerMergedModel))
        fragmentDetailsBinding.slider.apply {
            adapter = sliderAdapter
        }

        TabLayoutMediator(fragmentDetailsBinding.indicator, fragmentDetailsBinding.slider)
        { _, _ ->}.attach()
    }

    private fun getImageAnimalAndFlower(animalAndFlowerMergedModel: AnimalAndFlowerMergedModel) =
        listOf(animalAndFlowerMergedModel.animalImage, animalAndFlowerMergedModel.flowerImage)
}