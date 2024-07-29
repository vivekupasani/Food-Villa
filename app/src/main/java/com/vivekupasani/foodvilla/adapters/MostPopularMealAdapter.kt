package com.vivekupasani.foodvilla.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vivekupasani.foodvilla.databinding.MostPopularItemBinding
import com.vivekupasani.foodvilla.models.Category
import com.vivekupasani.foodvilla.models.CategoryMeals


class MostPopularMealAdapter() :
    RecyclerView.Adapter<MostPopularMealAdapter.viewHolder>() {

    private var mealList = ArrayList<CategoryMeals>()
    lateinit var onItemClick: ((CategoryMeals) -> Unit)


    fun setMeal(mealList: ArrayList<CategoryMeals>) {
        this.mealList = mealList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            MostPopularItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentMealList = mealList[position]
        Glide.with(holder.itemView).load(currentMealList.strMealThumb)
            .into(holder.binding.imgPopularMeal)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(currentMealList)
        }

    }

    class viewHolder(var binding: MostPopularItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}