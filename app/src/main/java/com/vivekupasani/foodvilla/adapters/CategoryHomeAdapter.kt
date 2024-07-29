package com.vivekupasani.foodvilla.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vivekupasani.foodvilla.databinding.CategoryItemBinding
import com.vivekupasani.foodvilla.models.Category

class CategoryHomeAdapter : RecyclerView.Adapter<CategoryHomeAdapter.viewHolder>() {
    class viewHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    private var popularList = ArrayList<Category>()
     var onItemClick: ((Category) -> Unit)? = null

    fun setCategoryList(popularList: ArrayList<Category>) {
        this.popularList = popularList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryHomeAdapter.viewHolder {
        val binding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryHomeAdapter.viewHolder, position: Int) {
        holder.apply {
            Glide.with(holder.itemView).load(popularList[position].strCategoryThumb)
                .into(holder.binding.imgCategory)

            binding.tvCategoryName.text = popularList[position].strCategory

            holder.itemView.setOnClickListener {

                onItemClick?.invoke(popularList[position])

            }
        }
    }

    override fun getItemCount(): Int {
        return popularList.size
    }
}