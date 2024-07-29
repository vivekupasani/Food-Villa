package com.vivekupasani.foodvilla.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.vivekupasani.foodvilla.adapters.CategoriesAdapter
import com.vivekupasani.foodvilla.databinding.ActivityCategoriesBinding
import com.vivekupasani.foodvilla.models.CategoryMeals
import com.vivekupasani.foodvilla.ui.Home
import com.vivekupasani.foodvilla.viewmodels.CategoriesViewModel

class categories : AppCompatActivity() {

    lateinit var binding: ActivityCategoriesBinding
    private val viewModel: CategoriesViewModel by viewModels()
    lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val category = intent.getStringExtra(Home.MEAL_CATEGORY)!!

        setUpRecyclerViewForCategories()
        viewModel.getCategories(category)
        observeCategories()
        onCategoriesItemClick()


    }

    private fun onCategoriesItemClick() {
        categoriesAdapter.onItemClick = { meal ->
            val intent = Intent(this, MealActivity::class.java).apply {
                putExtra(Home.MEAL_NAME, meal.strMeal)
                putExtra(Home.MEAL_THUMB, meal.strMealThumb)
                putExtra(Home.MEAL_ID, meal.idMeal)
            }
            startActivity(intent)
        }
    }


    private fun setUpRecyclerViewForCategories() {
        categoriesAdapter = CategoriesAdapter()
        binding.mealRecyclerview.apply {
            adapter = categoriesAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun observeCategories() {
        viewModel.observeCategoriesLiveData().observe(this, { categoryList ->
            categoriesAdapter.setCategory(categoryList as ArrayList<CategoryMeals>)
            binding.tvCategoryCount.text = "Found ${categoryList.size.toString()} items"
        })
    }
}