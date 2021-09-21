package com.example.mergeanimalsandflowers.utils

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.mergeanimalsandflowers.data.models.AnimalAndFlowerMergedModel
import com.google.android.material.imageview.ShapeableImageView


@BindingAdapter("visible")
fun View.bindVisible(visible: Boolean){
    visibility = if(visible) View.VISIBLE else View.GONE
}

@BindingAdapter("animalAndFlowerName")
fun AppCompatTextView.setText(animalAndFlowerMergedModel: AnimalAndFlowerMergedModel){
    text = animalAndFlowerMergedModel.animalName.plus(" / ").plus(animalAndFlowerMergedModel.flowerName)
}

@BindingAdapter("commonChars")
fun AppCompatTextView.setTextCommonChars(chars: List<Char>){
    text = chars.toString().replace("[", "").replace("]", "").replace(",", " ، ")
}

@BindingAdapter("android:src")
fun loadImage(view: ShapeableImageView, url: String) {
    Glide.with(view.context).load(url).into(view)
}





//@BindingAdapter("background")
//fun View.bindBackground(isSelected: Boolean){
//    setBackgroundResource(if (isSelected) R.drawable.bg_item_skill_selected else R.drawable.bg_item_skill)
//}

//@BindingAdapter("textColor")
//fun AppCompatTextView.bindTextColor(isSelected: Boolean){
//    setTextColor(if (isSelected) Color.WHITE else Color.BLACK)
//}

//@BindingAdapter("click")
//fun ButtonWithProgress.bindClick(onClickListener: View.OnClickListener){
//    setCallBack(onClickListener)
//}
//
//@BindingAdapter("backgroundQuestionState")
//fun View.bindBackgroundQuestionState(questionState: Int){
//    setBackgroundResource(
//        when (questionState) {
//            2 -> R.drawable.rounded_shape_fill_red
//            3 -> R.drawable.rounded_shape_fill_orange
//            else -> R.drawable.rounded_shape_fill_green
//        }
//    )
//}

//@BindingAdapter("textQuestionState")
//fun TextView.bindTextQuestionState(questionState: Int){
//    setText(
//        when (questionState) {
//            2 -> R.string.closed
//            3 -> R.string.poll
//            else -> R.string.open
//        }
//    )
//}
//
//@BindingAdapter("textSpecialities")
//fun TextView.bindTextSpecialities(items: List<AdviserSkillModel>){
//    val skills = StringBuilder()
//    items.forEach {
//        skills.append(it.title)
//        skills.append(" - ")
//    }
//    text = skills
//}
