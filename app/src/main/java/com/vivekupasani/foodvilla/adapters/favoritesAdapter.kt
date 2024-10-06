package com.vivekupasani.foodvilla.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vivekupasani.foodvilla.databinding.MealFavCardBinding
import com.vivekupasani.foodvilla.models.Meal

class favoritesAdapter(
    private val context: Context,
    var mealList: List<Meal>
) : RecyclerView.Adapter<favoritesAdapter.ViewHolder>() {

    var onItemClick: ((Meal) -> Unit)? = null
    var onDeleteClick: ((Meal) -> Unit)? = null

    class ViewHolder(val binding: MealFavCardBinding) : RecyclerView.ViewHolder(binding.root)

    fun setCategoryList(meal: ArrayList<Meal>) {
        this.mealList = meal
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MealFavCardBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMeal = mealList[position]

        holder.apply {
            Glide.with(holder.itemView).load(currentMeal.strMealThumb).into(binding.imgMeal)
            binding.tvMealName.text = currentMeal.strMeal
            binding.tvMealArea.text = currentMeal.strArea
            binding.tvMealCategory.text = currentMeal.strCategory

            itemView.setOnClickListener {
                onItemClick?.invoke(currentMeal)
            }

            binding.btnDelete.setOnClickListener {
                onDeleteClick?.invoke(currentMeal)
            }
        }
    }
}
