package com.example.mergeanimalsandflowers.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mergeanimalsandflowers.data.models.AnimalAndFlowerMergedModel
import com.example.mergeanimalsandflowers.databinding.FragmentDetailsBinding
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
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
            item = args.animalAndFlowerMergedModel
        }
        return fragmentDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleSlider(args.animalAndFlowerMergedModel)
    }

    private fun handleSlider(animalAndFlowerMergedModel: AnimalAndFlowerMergedModel) {
        val sliderAdapter = SliderAdapter(getImageAnimalAndFlower(animalAndFlowerMergedModel))
        fragmentDetailsBinding.slider.apply {
            setSliderAdapter(sliderAdapter)
            setIndicatorAnimation(IndicatorAnimationType.WORM)
        }
    }

    private fun getImageAnimalAndFlower(animalAndFlowerMergedModel: AnimalAndFlowerMergedModel) =
        listOf(animalAndFlowerMergedModel.animalImage, animalAndFlowerMergedModel.flowerImage)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }


}