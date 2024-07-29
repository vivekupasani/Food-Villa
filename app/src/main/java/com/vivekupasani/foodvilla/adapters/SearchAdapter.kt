package com.vivekupasani.foodvilla.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vivekupasani.foodvilla.databinding.MealCardBinding
import com.vivekupasani.foodvilla.models.Meal

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.viewHolder>() {

    private var categoryList: ArrayList<Meal> = ArrayList()
     var onItemClick: ((Meal) -> Unit)? = null

    fun setCategoryMeal(meal: ArrayList<Meal>) {
        this.categoryList = meal
        notifyDataSetChanged()
    }

    class viewHolder(val binding: MealCardBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = MealCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentList = categoryList[position]

        holder.apply {
            binding.tvMealName.text = currentList.strMeal
            Glide.with(holder.itemView).load(currentList.strMealThumb).into(binding.imgMeal)
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentList)
        }
    }
}