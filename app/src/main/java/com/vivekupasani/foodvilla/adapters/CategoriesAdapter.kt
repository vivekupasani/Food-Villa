package com.vivekupasani.foodvilla.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vivekupasani.foodvilla.databinding.MealCardBinding
import com.vivekupasani.foodvilla.models.CategoryMeals

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.viewHolder>() {


    private var CategoryList: ArrayList<CategoryMeals> = ArrayList()
     var onItemClick: ((CategoryMeals) -> Unit)? = null

    class viewHolder(val binding: MealCardBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    fun setCategory(mealList: ArrayList<CategoryMeals>) {
        this.CategoryList = mealList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = MealCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun getItemCount(): Int {
        return CategoryList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentMeal = CategoryList[position]

        holder.binding.tvMealName.text = currentMeal.strMeal
        Glide.with(holder.itemView).load(currentMeal.strMealThumb)
            .into(holder.binding.imgMeal)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentMeal)
        }
    }
}